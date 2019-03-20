package cz.levinzonr.studypad.presentation.screens.library.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.note_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import android.animation.AnimatorListenerAdapter
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.animation.Animator
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.common.NoteEditView
import kotlinx.android.synthetic.main.fragment_account_created.*


class NoteDetailFragment : BaseFragment(), NoteEditView.NoteEditViewListener {


    private val args: NoteDetailFragmentArgs by navArgs()


    override val viewModel: NoteDetailViewModel by viewModel { parametersOf(args.noteId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.editModeLiveData.observe(viewLifecycleOwner, Observer { editMode ->
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
            noteDetailView.setNoteDetails(it)
            noteEditView.setNoteDetails(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDetailView.setVisible(false)
        noteEditView.setVisible(false)

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
