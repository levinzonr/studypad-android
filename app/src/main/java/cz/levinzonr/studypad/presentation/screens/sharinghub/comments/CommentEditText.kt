package cz.levinzonr.studypad.presentation.screens.sharinghub.comments

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import kotlinx.android.synthetic.main.view_comment_edittext.view.*
import timber.log.Timber

class  CommentEditText @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, style: Int = 0) : LinearLayout(context, attributeSet, style){

    private var editMode: Boolean = false
    var listener: CommentEditTextListener? = null


    val editModeActive: Boolean
        get() = editMode

    init {
        context.layoutInflater.inflate(R.layout.view_comment_edittext, this, true)
        init()
    }

    private fun init() {
        editModeLayout.setVisible(editMode)
        changeModeBtn.setOnClickListener { clear() }
        setMode(false)
        sendButton.isEnabled = false

        commentInputField.onTextChanged {
            sendButton.isEnabled = it.isNotEmpty()
        }
    }


    fun editComment(comment: PublishedNotebook.Comment) {
        commentInputField.setText(comment.content)
        commentInputField.tag = comment
        setMode(true)
    }


    private fun setMode(edit: Boolean) {
        Timber.d("Set mode $edit")
        editMode = edit
        editModeLayout.setVisible(edit)

        if (editMode) {
            sendButton.setImageResource(R.drawable.ic_done)
            sendButton.setOnClickListener {
                listener?.onChangeConfirmButtonClicked(commentInputField.tag as PublishedNotebook.Comment, commentInputField.text.toString())
                clear()
            }
        } else {
            sendButton.setImageResource(R.drawable.ic_send_black_24dp)
            sendButton.setOnClickListener {
                listener?.onSendButtonClicked(commentInputField.text.toString())
                clear()
            }
        }
    }

    fun hideKeyboard() {
        commentInputField.hideKeyboard()
    }

    fun clear() {
        setMode(false)
        commentInputField.text.clear()
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(commentInputField.windowToken, 0)


    }


    interface CommentEditTextListener {
        fun onSendButtonClicked(text: String)
        fun onChangeConfirmButtonClicked(comment: PublishedNotebook.Comment, text: String)
    }

}