package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.agog.mathdisplay.MTMathView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import timber.log.Timber

class NoteContentView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {

    private var alignment: Alignment = Alignment.Normal

    init {
        init(attributeSet)
    }


    private fun init(attributeSet: AttributeSet?) {
        attributeSet?.let(this::initFromAttributes)
        orientation = VERTICAL
        gravity = if (alignment == Alignment.Center) Gravity.CENTER else Gravity.NO_GRAVITY

    }

    private fun initFromAttributes(attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.NoteContentView, 0, 0)
        val alInt = attrs.getInt(R.styleable.NoteContentView_alignment, 0)
        alignment = Alignment.values()[alInt]
    }

    private val regex = Regex("(?:^|.)`([^`]+?)`(?:\$|.)")
    private val fontSize = 18f

    var text: String = ""
        set(value) {
            field = value
            updateView()
        }

    private fun updateView() {
       removeAllViews()
        val mathViews = regex.findAll(text, 0)
            .mapNotNull { it.range }
            .toList()
            .map { IntRange(it.start, it.endInclusive) }
            .filterNotEmpty()
            .map { ContentView(it, true) }

        val ranges = regex.split(text)
            .map { IntRange(text.indexOf(it), text.indexOf(it) + it.length - 1) }
            .filterNotEmpty()
            .map { ContentView(it, false) }

        listOf(mathViews, ranges)
            .flatten()
            .sortedBy { it.range.first }
            .forEach(this::addContentView)

        Timber.d("Other Ranges: $ranges")
        Timber.d("MathRanges; $mathViews")
    }

    internal data class ContentView(val range: IntRange, val math: Boolean = false)

    private fun List<IntRange>.filterNotEmpty(): List<IntRange> {
        return filter {  it.endInclusive != 0 }.filter { it.endInclusive != -1 }
    }

    private fun TextView.setAlignment(alignment: Alignment) {
        if (alignment == Alignment.Center) {
            gravity = Gravity.CENTER
        }
    }

    private fun addContentView(contentView: ContentView) {

        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.gravity = if (Alignment.Center == alignment) Gravity.CENTER else Gravity.NO_GRAVITY
        val textToAdd = text.substring(contentView.range)
        val child = if (contentView.math) {
            MTMathView(context).apply {
                layoutParams = params
                latex = textToAdd.replace("`", "")
                    .trim().replace(" ", "\\ ")
                fontSize = MTMathView.convertDpToPixel(this@NoteContentView.fontSize)
            }
        } else {
            TextView(context).apply {
                text = textToAdd
                layoutParams = params
                setAlignment(alignment)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize - 2)
            }
        }
        addView(child)
    }

    enum class Alignment {
        Normal, Center
    }
}
