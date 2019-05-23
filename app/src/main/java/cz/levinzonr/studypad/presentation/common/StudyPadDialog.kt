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
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.dialog_generic.*

/**
 * Generic dialog that is used across the application
 */
class StudyPadDialog(context: Context?) : Dialog(context) {

    private var negativeButtonText: String? = null
    private var negativeButtonListener: ((Dialog) -> Unit)? = null

    private var postiveButtonText: String? = null
    private var positiveButtonListener: ((Dialog) -> Unit)? = null

    private var inputFieldListener: ((String) -> Unit)? = null
    private var inputFieldHint: String = ""

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

        if (inputFieldListener != null) {
            textInputLayout3.hint = inputFieldHint
            textInputLayout3.setVisible(true)
            buttonPositive.isEnabled = false
            inputField.onTextChanged { buttonPositive.isEnabled = it.isNotBlank() }
            setupButton(buttonPositive, context.getString(R.string.default_confirm)) {
                inputFieldListener?.invoke(inputField.text?.toString() ?: "")
                it.dismiss()
            }
            setupButton(buttonNegative, context.getString(R.string.default_cancel)) {
                it.dismiss()
            }
        }

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


    /**
     * Builder that is used to build the dialog
     */
    class Builder(context: Context?) {
        private val dialog = StudyPadDialog(context)

        /**
         * Add a negative button to dialog
         * @param text - button's text
         * @param onClick - action to trigger when button is clicked
         * @return current builder instance
         */
        fun setNegativeButton(text: String?, onClick: (Dialog) -> Unit): Builder {
            dialog.negativeButtonText = text
            dialog.negativeButtonListener = onClick
            return this
        }

        /**
         * Sets a positive button to the dialog
         * @param text - button's text
         * @param onClick - action to trigger when button is clicked
         * @return current builder instance
         */
        fun setPositiveButton(text: String?, onClick: (Dialog) -> Unit): Builder {
            dialog.postiveButtonText = text
            dialog.positiveButtonListener = onClick
            return this
        }

        /**
         * Add an input fied the get the text input from the user
         * @param hint - hint of the input field
         * @param onConffrm - a callback with the inserted value
         * @return current builder instance
         */
        fun setInputField(hint: String, onConffrm: (String) -> Unit) : Builder {
            dialog.inputFieldHint = hint
            dialog.inputFieldListener = onConffrm
            return this
        }


        /**
         * Set title of the dialog
         * @param text - title of the dialog
         * @return current builder instance
         */
        fun setTitle(text: String?): Builder {
            dialog.title = text ?: ""
            return this
        }

        /**
         * Set message for the dialog
         * @param text - dialog's message
         * @return current builder instance
         */
        fun setMessage(text: String?): Builder {
            dialog.message = text ?: ""
            return this
        }


        /**
         * Define with the dialog will be cancellable
         * @param cancelable - true to make if cancellable
         * @return current builder instance
         */
        fun setCancelable(cancelable: Boolean): Builder {
            dialog.setCancelable(cancelable)
            return this
        }

        /**
         * Specify a callback when dialog is cancelled
         * @param onCancel - callback when dialogs is cancelled
         * @return current builder instance
         */
        fun setOnCancelListener(onCancel: () -> Unit) : Builder {
            dialog.cancelListener = onCancel
            return this
        }

        /**
         * Build & show the dialog
         */
        fun show() {
            dialog.show()
        }

        /**
         * Build dialog
         * @return setup dialog
         */
        fun build(): Dialog {
            return dialog
        }


    }

}