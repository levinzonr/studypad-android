package cz.levinzonr.studypad.presentation.screens.library.notebooks

import androidx.fragment.app.FragmentManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog

class NotebookBottomMenu : BottomSheetDialog() {

    override val layoutResource: Int = R.layout.notebook_bottom_menu

    companion object {

        fun show(fragmentMaager: FragmentManager, onSelected: (Int) -> Unit) {
            val fragment = NotebookBottomMenu()
            fragment.onOptionSelected = onSelected
            fragment.show(fragmentMaager, fragment.tag)
        }


    }

}