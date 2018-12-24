package cz.levinzonr.studyhub.presentation.screens.library

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.Notebook
import kotlinx.android.synthetic.main.dialog_edit_notebook.*
import kotlinx.android.synthetic.main.dialog_edit_notebook.view.*

class EditNotebookDialog : DialogFragment() {

    private val notebook : Notebook?
        get() = arguments?.getParcelable(ARG_NOTEBOOK)

    private var callback: (String) -> Unit = {}

    private lateinit var inputEditText: TextInputEditText
    private lateinit var button: MaterialButton
    companion object {

        private const val TAG = "EditNotebook"
        private const val ARG_NOTEBOOK = "Notebook"

        fun show(fm: FragmentManager?, notebook: Notebook? = null, onComplete: (String) -> Unit) {
            fm ?: return
            val fragment = fm.findFragmentByTag(TAG) as? EditNotebookDialog? ?: EditNotebookDialog()
            fragment.arguments = Bundle().apply { putParcelable(ARG_NOTEBOOK, notebook) }
            fragment.callback = onComplete
            fragment.show(fm, TAG)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_notebook, null, false)
        initView(view)
        return AlertDialog.Builder(context)
            .setView(view)
            .create()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextInputEditText>(R.id.notebookEditEt).setText(notebook?.name ?: "")

        view.findViewById<MaterialButton>(R.id.confirmBtn).setOnClickListener {
            callback.invoke(notebookEditEt.text.toString())
            dismiss()
        }
    }

    private fun initView(view: View) {
        inputEditText = view.findViewById(R.id.notebookEditEt)
        button = view.findViewById(R.id.confirmBtn)

       button?.setOnClickListener {
           callback?.invoke(inputEditText.text.toString())
           dismiss()
       }

    }
}