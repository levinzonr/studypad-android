package cz.levinzonr.studypad.presentation.screens.library.notes

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.baseActivity
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_note_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import cz.levinzonr.studypad.presentation.common.NoteEditView
import timber.log.Timber




class NoteDetailFragment : BaseFragment(), NoteEditView.NoteEditViewListener {


    private val args: NoteDetailFragmentArgs by navArgs()

    private var menu: Menu? = null
    override val viewModel: NoteDetailViewModel by viewModel { parametersOf(args.viewMode) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }


    override fun subscribe() {
        viewModel.noteLiveData.observe(viewLifecycleOwner, Observer {
            Timber.d("Note update $it")
            noteDetailView.setNoteDetails(it)
            noteEditView.setNoteDetails(it)
        })

        viewModel.editModeLiveData.observe(viewLifecycleOwner, Observer { editMode ->
            noteDetailView.setVisible(!editMode)
            noteEditView.setVisible(editMode)
            noteEditView.showFocused(editMode)
            this.menu?.getItem(0)?.isVisible = editMode
            this.menu?.getItem(1)?.isVisible = !editMode
            this.menu?.getItem(2)?.isVisible = !editMode
            val res = if (editMode) R.drawable.ic_done else R.drawable.ic_edit
            noteDetailEditFab.setImageResource(res)

        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteEditView.listener = this
        noteDetailEditFab.setOnClickListener {
            viewModel.handleModeChangeButton()
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
    }

    override fun showNetworkUnavailableError() {
        showSimpleDialog(getString(R.string.error_network_title), getString(R.string.error_network_message))
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        noteEditView.showFocused(false)
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_note_detail, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val note = viewModel.noteLiveData.value
        when(item.itemId) {
            R.id.noteDelete -> viewModel.deleteNote()
            R.id.noteCopy -> note?.let {
                copyToClipboard("${note.title}\n${note.content}")
            }
            R.id.noteAddMathView -> noteEditView.addMathView()
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        noteEditView.listener = null
    }

    override fun onNoteChanged(title: String, content: String) {
        viewModel.content = content
        viewModel.title = title
    }

}
