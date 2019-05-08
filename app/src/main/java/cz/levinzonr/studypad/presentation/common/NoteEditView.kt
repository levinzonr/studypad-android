package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.hideKeyboard
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.showKeyboard
import kotlinx.android.synthetic.main.view_note_edition.view.*

class NoteEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val noteContentTv: EditText
    private val noteTitleTv: EditText

    var listener: NoteEditViewListener? = null

    private var lastlyFocused: EditText? = null

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

        noteTitleTv.setOnFocusChangeListener { view, b ->
            if (b) lastlyFocused = view as EditText
        }

        noteContentTv.setOnFocusChangeListener { view, b ->
            if (b) lastlyFocused = view as EditText
        }

    }


    fun setNoteDetails(note: Note) {
        noteContentTv.setText(note.content)
        noteTitleTv.setText(note.title)

    }

    fun showFocused(focused: Boolean) {
        if (focused) {
            val viewToFoucus = if (noteTitleTv.text.isEmpty()) noteTitleTv else noteContentTv
            viewToFoucus.requestFocus()
            viewToFoucus.showKeyboard()
            viewToFoucus.setSelection(viewToFoucus.text.length)
        } else {
            lastlyFocused?.hideKeyboard()
        }
    }


    fun addMathView() {
        val cursorPos = noteContentTv.selectionStart
        val toInser = "`LaTeX formula`"
        noteContentEt.text?.insert(cursorPos, toInser)
        noteContentEt.setSelection(cursorPos + 1, cursorPos + toInser.length  - 1)
    }

    interface NoteEditViewListener {
        fun onNoteChanged(title: String, content: String)
    }
}