package cz.levinzonr.studypad.presentation.screens.selectors.language

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.item_language.view.*

class LanguagesAdapter(val listener: LanguageItemListener) : ListAdapter<Locale, LanguagesAdapter.ViewHolder>(
    TaskDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context.layoutInflater.inflate(R.layout.item_language, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val showLetter = when (position) {
            0 -> true
            else -> getItem(position - 1).displayName.first() != item.displayName.first()
        }

        holder.bindView(item, showLetter)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(locale: Locale, showLetter: Boolean) {
            view.languageLetterTv.setVisible(showLetter)
            view.languageLetterTv.text = locale.displayName.first().toString()
            view.languageTv.text = locale.displayName
            view.setOnClickListener { listener.onLanguageSelected(locale) }
        }
    }

    interface LanguageItemListener {
        fun onLanguageSelected(locale: Locale)
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Locale>() {

        override fun areItemsTheSame(oldItem: Locale, newItem: Locale): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Locale, newItem: Locale): Boolean {
            return oldItem.code == newItem.code
        }
    }


}