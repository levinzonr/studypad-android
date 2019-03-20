package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.onTextChanged

class NoteEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val noteContentTv: EditText
    private val noteTitleTv: EditText

    var listener: NoteEditViewListener? = null

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.view_note_edition, this, true) as NoteEditView
        noteContentTv = root.findViewById(R.id.noteContentEt)
        noteTitleTv  = root.findViewById(R.id.noteTitleEt)
        initView()
    }

    private fun initView() {

        noteContentTv.onTextChanged {
            listener?.onNoteChanged(noteTitleTv.text.toString(), it)
        }

        noteTitleTv.onTextChanged {
            listener?.onNoteChanged(it, noteContentTv.text.toString())
        }

    }


    fun setNoteDetails(note: Note) {
        noteContentTv.setText(note.content)
        noteTitleTv.setText(note.title)

    }

    interface NoteEditViewListener {
        fun onNoteChanged(title: String, content: String)
    }
}