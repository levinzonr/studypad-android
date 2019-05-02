package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import kotlinx.android.synthetic.main.view_note.view.*
import org.w3c.dom.Text

class NoteDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {



    init {
        val root = LayoutInflater.from(context).inflate(R.layout.view_note, this, true) as NoteDetailView
        initView()
    }

    private fun initView() {

    }


    fun setNoteDetails(note: Note) {
        noteContentTv.text = note.content ?: ""

        noteTitleTv.text = note.title

    }
}