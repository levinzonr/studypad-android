package cz.levinzonr.studypad.presentation.screens.sharinghub.details


import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.common.NoteDetailView
import kotlinx.android.synthetic.main.fragment_published_note_detail_dialog.*

class PublishedNoteDetailDialog : DialogFragment() {

    private val note: Note?
        get() = arguments?.getParcelable(ARG_NOTE)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = context?.layoutInflater?.inflate(R.layout.fragment_published_note_detail_dialog, null, false)
        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        note?.let { view?.findViewById<NoteDetailView>(R.id.noteDetailView)?.setNoteDetails(it) }
        return dialog

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        note?.let(noteDetailView::setNoteDetails)
    }



    companion object {

        private const val ARG_NOTE = "arg_note"
        private const val TAG = "dialogNote"

        fun show(fm: FragmentManager, note: Note) {
            val frag = fm.findFragmentByTag(TAG) as? PublishedNoteDetailDialog? ?: PublishedNoteDetailDialog()
            frag.arguments = Bundle().apply { putParcelable(ARG_NOTE, note) }
            frag.show(fm, TAG)
        }
    }
}
