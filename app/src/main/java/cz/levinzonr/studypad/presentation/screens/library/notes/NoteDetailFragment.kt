package cz.levinzonr.studypad.presentation.screens.library.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_note_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import cz.levinzonr.studypad.presentation.common.NoteEditView
import timber.log.Timber




class NoteDetailFragment : BaseFragment(), NoteEditView.NoteEditViewListener {


    private val args: NoteDetailFragmentArgs by navArgs()


    override val viewModel: NoteDetailViewModel by viewModel { parametersOf(args.viewMode) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            it.handle { showToast(it) }
        })

        viewModel.editModeLiveData.observe(viewLifecycleOwner, Observer { editMode ->
            val distance = 123123123
            val scale = resources.displayMetrics.density * distance
            noteDetailView.setCameraDistance(scale)
            noteDetailView.setCameraDistance(scale)
            Timber.d("Note mode: $editMode")
            detail.animate()
                .rotationY(90f)
                .setDuration(300L)
                .withEndAction {
                    // second quarter turn
                    detail.setRotationY(-90f);
                    detail.animate()
                        .rotationY(0f)
                        .setDuration(300).withEndAction {
                            noteDetailView.setVisible(!editMode)
                            noteEditView.setVisible(editMode)
                        }
                        .start()
                }.start()
        })


        viewModel.noteLiveData.observe(viewLifecycleOwner, Observer {
            Timber.d("Note update $it")
            noteDetailView.setNoteDetails(it)
            noteEditView.setNoteDetails(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteEditView.listener = this
        noteDetailEditFab.setOnClickListener {
            viewModel.handleModeChangeButton()
        }


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
