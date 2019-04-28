package cz.levinzonr.studypad.presentation.common


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import cz.levinzonr.studypad.R
import kotlinx.android.synthetic.main.dialog_generic.*

class StudyPadDialog(context: Context?) : Dialog(context) {

    private var negativeButtonText: String? = null
    private var negativeButtonListener: ((Dialog) -> Unit)? = null

    private var postiveButtonText: String? = null
    private var positiveButtonListener: ((Dialog) -> Unit)? = null

    private var cancelListener: () -> Unit = {}

    private var message: String = ""
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.dialog_generic, null, false)
        setContentView(view)
        negativeButtonText?.let { setupButton(buttonNegative, it, negativeButtonListener) }
        postiveButtonText?.let { setupButton(buttonPositive, it, positiveButtonListener) }

        setOnCancelListener { cancelListener.invoke() }


        dialogTitle.text = title
        dialogMessage.text = message
    }


    private fun setupButton(button: Button, text: String?, onClick: ((Dialog) -> Unit)?) {
        text?.let { text ->
            button.text = text
            button.visibility = View.VISIBLE
            button.setOnClickListener { onClick?.invoke(this) }
        }
    }


    override fun onStart() {
        super.onStart()
        setupDialog()
    }

    private fun setupDialog() {
        val max = WindowManager.LayoutParams.MATCH_PARENT * 0.2
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT - max.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogLayoutParams = window?.attributes
        window?.attributes = dialogLayoutParams
    }


    class Builder(context: Context?) {
        private val dialog = StudyPadDialog(context)

        fun setNegativeButton(text: String?, onClick: (Dialog) -> Unit): Builder {
            dialog.negativeButtonText = text
            dialog.negativeButtonListener = onClick
            return this
        }

        fun setPositiveButton(text: String?, onClick: (Dialog) -> Unit): Builder {
            dialog.postiveButtonText = text
            dialog.positiveButtonListener = onClick
            return this
        }


        fun setTitle(text: String?): Builder {
            dialog.title = text ?: ""
            return this
        }

        fun setMessage(text: String?): Builder {
            dialog.message = text ?: ""
            return this
        }


        fun setCancelable(cancelable: Boolean): Builder {
            dialog.setCancelable(cancelable)
            return this
        }

        fun setOnCancelListener(onCancel: () -> Unit) : Builder {
            dialog.cancelListener = onCancel
            return this
        }

        fun show() {
            dialog.show()
        }

        fun build(): Dialog {
            return dialog
        }


    }

}