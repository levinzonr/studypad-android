package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R

class EntryChip @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    style : Int = R.style.Widget_MaterialComponents_Chip_Entry
) : Chip(context, attrs, style) {

    init {
        isCloseIconVisible = true
        isClickable = true
        isCheckable = false
    }


}