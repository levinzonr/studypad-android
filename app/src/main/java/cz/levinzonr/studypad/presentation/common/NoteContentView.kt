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

/**
 * Widget used to display notes content, including the rendering of math formulas
 */
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


    /**
     * Regex to find all the text that is supposed to be rendered as Formula
     */
    private val regex = Regex("(?:^|.)`([^`]+?)`(?:\$|.)")

    /**
     * Output font size
     */
    private val fontSize = 18f


    /**
     * Represent the current text displayed
     * Setting the new text will re-render the view
     */
    var text: String = ""
        set(value) {
            field = value.replace("`", " ` ")
            updateView()
        }

    private fun updateView() {
       removeAllViews()

        // Finds all the math views and wraps them up in ContentView
        val mathViews = regex.findAll(text, 0)
            .mapNotNull { it.range }
            .map { IntRange(it.start, it.endInclusive) }
            .toList()
            .filterNotEmpty()
            .map { ContentView(it, true) }


        // Finds all the other text and wraps them into ContentView
        val ranges = regex.split(text)
            .map { IntRange(text.indexOf(it), text.indexOf(it) + it.length - 1) }
            .filterNotEmpty()
            .map { ContentView(it, false) }

        // prepare all the text and add them as views
        listOf(mathViews, ranges)
            .flatten()
            .sortedBy { it.range.first }
            .forEach(this::addContentView)

        Timber.d("Other Ranges: $ranges")
        Timber.d("MathRanges; $mathViews")
    }

    /**
     * The view to hold the text range and the way to render them
     * @param range - The text range to render
     * @param math - determines how the text will be rendered, when set to true, it will be rendered as Math formula
     */
    internal data class ContentView(val range: IntRange, val math: Boolean = false)

    /**
     * A collection extenstion to filter over the empty intRanges
     */
    private fun Collection<IntRange>.filterNotEmpty(): List<IntRange> {
        return filter {  it.endInclusive != 0 }.filter { it.endInclusive != -1 }
    }

    private fun TextView.setAlignment(alignment: Alignment) {
        if (alignment == Alignment.Center) {
            gravity = Gravity.CENTER
        }
    }


    /**
     * Adds a content view to the root layout
     * @param contentView - view to add
     */
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
