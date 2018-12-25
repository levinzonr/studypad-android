package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import kotlinx.android.synthetic.main.dialog_edit_notebook.*

class EditNotebookDialog : DialogFragment() {

    private val notebook: Notebook?
        get() = arguments?.getParcelable(ARG_NOTEBOOK)

    private var onEdit: ((Notebook?, String) -> Unit) = { notebook, name -> }

    private lateinit var inputEditText: TextInputEditText
    private lateinit var button: MaterialButton

    companion object {

        private const val TAG = "EditNotebook"
        private const val ARG_NOTEBOOK = "Notebook"

        fun show(fm: FragmentManager?, notebook: Notebook? = null, onComplete: ((Notebook?, String) -> Unit)) {
            fm ?: return
            val fragment = fm.findFragmentByTag(TAG) as? EditNotebookDialog? ?: EditNotebookDialog()
            fragment.arguments = Bundle().apply { putParcelable(ARG_NOTEBOOK, notebook) }
            fragment.onEdit = onComplete
            fragment.show(
                fm,
                TAG
            )
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_notebook, null, false)
        initView(view)
        return AlertDialog.Builder(context)
            .setView(view)
            .create()

    }


    private fun initView(view: View) {
        inputEditText = view.findViewById(R.id.notebookEditEt)
        button = view.findViewById(R.id.confirmBtn)

        inputEditText.setText(notebook?.name ?: "")


        button.setOnClickListener {
            onEdit.invoke(notebook, inputEditText.text.toString())
            dismiss()
        }
    }
}