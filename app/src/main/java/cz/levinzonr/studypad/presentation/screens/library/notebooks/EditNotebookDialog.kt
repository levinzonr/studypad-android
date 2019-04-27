package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import kotlinx.android.synthetic.main.dialog_edit_notebook.*

class EditNotebookDialog : BottomSheetDialogFragment() {

    private val notebook: Notebook?
        get() = arguments?.getParcelable(ARG_NOTEBOOK)

    private var onEdit: ((Notebook?, String) -> Unit) = { notebook, name -> }

    companion object {

        private const val TAG = "EditNotebook"
        private const val ARG_NOTEBOOK = "Notebook"

        fun show(fm: FragmentManager?, notebook: Notebook? = null, onComplete: ((Notebook?, String) -> Unit)) {
            fm ?: return
            val fragment = fm.findFragmentByTag(TAG) as? EditNotebookDialog? ?: EditNotebookDialog()
            fragment.arguments = Bundle().apply { putParcelable(ARG_NOTEBOOK, notebook) }
            fragment.onEdit = onComplete
            fragment.show(fm, TAG)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.dialog_edit_notebook, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = if (notebook != null) R.string.library_notebook_edit_title else R.string.library_notebook_create_title
        val message = if (notebook != null) R.string.library_notebook_edit_message else R.string.library_notebook_create_message

        editNotebookTitleTv.setText(title)
        editNotebookContentTv.setText(message)

        confirmBtn.setOnClickListener {
            onEdit.invoke(notebook, notebookEditEt.text.toString())
            dismiss()
        }

        notebookEditEt.setText(notebook?.name ?: "")

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }
}