package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.base.BottomSheetOptionsDialog
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.notebook_bottom_menu.*

class NotebookBottomMenuOptions : BottomSheetOptionsDialog() {

    override val layoutResource: Int = R.layout.notebook_bottom_menu

    private val notebook: Notebook
        get() = arguments?.getParcelable(ARG_NOTEBOOK)!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notebookPublishBtn.setVisible(notebook.exportedId == null && notebook.sourceId == null)
        notebookOpenShared.setVisible(notebook.exportedId != null || notebook.sourceId != null)

    }

    companion object {

        private const val ARG_NOTEBOOK = "Notebook"

        fun show(fragmentMaager: FragmentManager, notebook: Notebook, onSelected: (Int) -> Unit) {
            val fragment = NotebookBottomMenuOptions()
            fragment.onOptionSelected = onSelected
            fragment.arguments = Bundle().apply { putParcelable(ARG_NOTEBOOK, notebook) }
            fragment.show(fragmentMaager, fragment.tag)
        }


    }

}