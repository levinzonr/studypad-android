package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nishant.math.MathView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import kotlinx.android.synthetic.main.view_note.view.*
import org.w3c.dom.Text

class NoteDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val noteContentTv: MathView
    private val noteTitleTv: TextView


    init {
        val root = LayoutInflater.from(context).inflate(R.layout.view_note, this, true) as NoteDetailView
        noteContentTv = root.findViewById(R.id.noteContentTv)
        noteTitleTv  = root.findViewById(R.id.noteTitleTv)
        initView()
    }

    private fun initView() {

        noteTitleTv.setOnClickListener {
            noteContentTv.text = "Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Cras justo odio, dapibus ac facilisis in, egestas eget quam.\n" +
                    "\n" +
                    "Donec ullamcorper nulla non metus auctor fringilla. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Nulla vitae elit libero, a pharetra augue. Donec ullamcorper nulla non metus auctor fringilla. Sed posuere consectetur est at lobortis.\n" +
                    "\n" +
                    "Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras mattis consectetur purus sit amet fermentum. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Maecenas sed diam eget risus varius blandit sit amet non magna. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.\n" +
                    "\n" +
                    "Nullam id dolor id nibh ultricies vehicula ut id elit. Nullam id dolor id nibh ultricies vehicula ut id elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Curabitur blandit tempus porttitor."
        }
    }


    fun setNoteDetails(note: Note) {
        noteContentTv.text = note.content
        noteTitleTv.text = note.title

    }
}