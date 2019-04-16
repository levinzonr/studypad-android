package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R
import timber.log.Timber

class FilterChip @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, s: Int = 0) : Chip(context, attributeSet, s) {
    var isActive: Boolean = false
    var listener: FilterClickListener? = null
    val defaultBackgroundColor = backgroundTintList

    private val inactiveText = text
    init {
        isCloseIconVisible = true
        setChipInactive()
    }

    fun setChipActive(activeText: String) {
        isActive = true
        text = activeText
        setCloseIconResource(R.drawable.ic_close_black_24dp)
        setOnCloseIconClickListener { listener?.onFilterClicked(true) }
        setOnClickListener { listener?.onFilterClicked() }
        updateView()
    }

    fun setChipInactive() {
        isActive = false
        text = inactiveText
        setCloseIconResource(R.drawable.ic_expand_more_black_24dp)
        setOnCloseIconClickListener { listener?.onFilterClicked() }
        setOnClickListener { listener?.onFilterClicked() }
        updateView()
    }

    private fun updateView() {
        val backgroundColor = if (isActive) R.color.blue else R.color.greysih
        val foregroundColor = if(isActive) ContextCompat.getColor(context, R.color.lightGrey) else Color.BLACK
        setTextColor(foregroundColor)
        chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, backgroundColor))
        chipIconTint = ColorStateList.valueOf(foregroundColor)
        closeIconTint = ColorStateList.valueOf(foregroundColor)
    }




    interface FilterClickListener {
        fun onFilterClicked(clearFilter: Boolean = false)
    }

    fun setFilterListener(block: (clearFilter: Boolean) -> Unit) {
        listener = object : FilterClickListener {
            override fun onFilterClicked(clearFilter: Boolean) {
                block.invoke(clearFilter)
            }
        }
    }
}