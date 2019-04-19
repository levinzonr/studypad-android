package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.buildTags
import timber.log.Timber

class MaxLinesChipGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ChipGroup(context, attributeSet, defStyle) {

    val maxLines: Int

    init {
        val attrsSet = context.obtainStyledAttributes(attributeSet, R.styleable.MaxLinesChipGroup, 0, 0)
        maxLines = attrsSet?.getInteger(R.styleable.MaxLinesChipGroup_maxLines, 2) ?: 2
        attrsSet.recycle()
    }

    var prevLinesWidth = 0
    var lines = -1

    private fun addChip(chip: Chip): Boolean {
        val parent = parent as ViewGroup
        measure(parent.width, parent.height)
        chip.measure(width, height)

        if ((measuredWidth + chip.measuredWidth) - prevLinesWidth > parent.width) {
            lines++
            prevLinesWidth = measuredWidth
        }

        // Timber.v("layout line: $lines ${group.measuredWidth} x ${group.measuredHeight} - $s")

        return if ((lines <= maxLines) || (maxLines < 0)) {
            // only add views up till the given number of lines, or just add all
            addView(chip)
            true
        } else {
            false
        }
    }


    fun addAll(list: List<String>) {
        val itemsLeft = list.buildTags(context)
            .map { addChip(it) }.also { Timber.d("LEft : $it") }
            .filter { !it }.size

        Timber.d("Left $itemsLeft")
        if (itemsLeft > 0) {
            val text = TextView(context).apply { text = "and $itemsLeft more" }
            addView(text)
        }

    }


}