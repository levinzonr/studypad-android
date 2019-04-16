package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import kotlinx.android.synthetic.main.view_square_button.view.*
import timber.log.Timber

class SqureButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, def: Int = 0) : LinearLayout(context, attrs, def) {

    init {
        context.layoutInflater.inflate(R.layout.view_square_button, this, true)
        init()
    }

    private fun init() {
        root.setOnClickListener {
            Timber.d("SADa")
            imageButton.isEnabled = !imageButton.isEnabled
        }
    }

    var isChecked: Boolean = false
        set(value) {
            field = value
            updateChecked()

        }


    private fun updateChecked() {
        Timber.d("SADa")

        imageButton.isEnabled = !imageButton.isEnabled

    }
}
