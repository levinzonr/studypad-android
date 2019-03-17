package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import kotlinx.android.synthetic.main.dialog_edit_notebook.*

class EditNotebookDialog : cz.levinzonr.studypad.presentation.base.BottomSheetDialog() {

    private val notebook: Notebook?
        get() = arguments?.getParcelable(ARG_NOTEBOOK)

    private var onEdit: ((Notebook?, String) -> Unit) = { notebook, name -> }

    private lateinit var inputEditText: TextInputEditText
    private lateinit var button: MaterialButton
    private lateinit var cancelButton : MaterialButton

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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_edit_notebook, container, false)
        initView(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private fun initView(view: View) {
        inputEditText = view.findViewById(R.id.notebookEditEt)


        button = view.findViewById(R.id.confirmBtn)
        cancelButton = view.findViewById(R.id.cancelBtn)

        inputEditText.setText(notebook?.name ?: "")


        button.setOnClickListener {
            onEdit.invoke(notebook, inputEditText.text.toString())
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputEditText.requestFocus()
    }

}