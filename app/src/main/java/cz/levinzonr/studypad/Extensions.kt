package cz.levinzonr.studypad

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.ActionBar
import com.google.android.material.textfield.TextInputEditText
import cz.levinzonr.studypad.presentation.base.BaseActivity
import cz.levinzonr.studypad.presentation.base.BaseFragment


val BaseFragment.baseActivity : BaseActivity?
    get() = activity as? BaseActivity?

val BaseFragment.supportActionBar: ActionBar?
    get() = baseActivity?.supportActionBar


fun TextInputEditText.onTextChanged(onChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChange.invoke(p0.toString())
        }
    })
}

fun View.setVisible(visible: Boolean, fallback: Int = View.GONE) {
    visibility =  if (visible)  View.VISIBLE else fallback
}