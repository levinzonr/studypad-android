package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import kotlinx.android.synthetic.main.view_square_button.view.*
import timber.log.Timber



class SquareButton @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, def: Int = 0) : LinearLayout(context, attrs, def) {

    init {
        context.layoutInflater.inflate(R.layout.view_square_button, this, true)
        init()
    }

    private fun init() {
        initFromAttrs(attrs)
        root.setOnClickListener {
            performClick()
        }


    }


    private fun initFromAttrs(attrs: AttributeSet?) {
        val attrsSet = context.obtainStyledAttributes(attrs, R.styleable.SquareButton, 0, 0)
        val buttonDrawable = attrsSet.getDrawable(R.styleable.SquareButton_buttonIcon)
        val buttonText = attrsSet.getString(R.styleable.SquareButton_buttonText)

        buttonDrawable?.let(imageButton::setImageDrawable)
        buttonText?.let(imageTextView::setText)


        attrsSet.recycle()
    }



    var isChecked: Boolean = false
        set(value) {
            field = value
            imageButton.isEnabled = !field

        }
}
