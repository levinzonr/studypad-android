package cz.levinzonr.studypad.presentation.screens.library.notebooks

import androidx.fragment.app.FragmentManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BottomSheetOptionsDialog

class NotebookBottomMenuOptions : BottomSheetOptionsDialog() {

    override val layoutResource: Int = R.layout.notebook_bottom_menu

    companion object {

        fun show(fragmentMaager: FragmentManager, onSelected: (Int) -> Unit) {
            val fragment = NotebookBottomMenuOptions()
            fragment.onOptionSelected = onSelected
            fragment.show(fragmentMaager, fragment.tag)
        }


    }

}