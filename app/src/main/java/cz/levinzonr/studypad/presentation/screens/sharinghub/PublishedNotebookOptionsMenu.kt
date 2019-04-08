package cz.levinzonr.studypad.presentation.screens.sharinghub

import androidx.fragment.app.FragmentManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BottomSheetOptionsDialog

class PublishedNotebookOptionsMenu : BottomSheetOptionsDialog() {

    override val layoutResource: Int
        get() = R.layout.bottom_menu_published_notebook


    companion object {


        fun show(fragmentMaager: FragmentManager, onSelected: (Int) -> Unit) {
            val fragment = PublishedNotebookOptionsMenu()
            fragment.onOptionSelected = onSelected
            fragment.show(fragmentMaager, fragment.tag)
        }


    }

}