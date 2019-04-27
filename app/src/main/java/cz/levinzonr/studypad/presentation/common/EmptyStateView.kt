package cz.levinzonr.studypad.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.setVisible
import cz.levinzonr.studypad.shownText
import kotlinx.android.synthetic.main.view_empty_state.view.*

class EmptyStateView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, style: Int = 0) : LinearLayout(context, attributeSet, style) {

    init {
        context.layoutInflater.inflate(R.layout.view_empty_state, this, true)
    }

    fun configure(title: String? = null, message: String? = null, imageView: Int? = null) {
        emptyViewTitleTv.shownText = title
        emptyViewMessageTv.shownText = message
        imageView?.let(emptyViewIv::setImageResource)
        emptyViewIv.setVisible(imageView != null)
    }

    fun configure(titleRes: Int? = null, messageRes: Int? = null, imageRes: Int? = null) {
        val title = titleRes?.let(context::getString)
        val message = messageRes?.let(context::getString)
        configure(title, message, imageRes)
    }

    val imageView: ImageView
        get() =  emptyViewIv

    fun configureAsNetworkError() {
        configure("You're offline", "Please check your internet connection", R.drawable.ic_cloud_off_black_24dp)
    }

}