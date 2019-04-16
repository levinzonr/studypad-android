package cz.levinzonr.studypad.presentation.adapters

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels
import cz.levinzonr.studypad.setSpannableText
import kotlinx.android.synthetic.main.item_suggestion.view.*

class SuggestionsAdapter : ListAdapter<SuggestionsModels.SuggestionItem, SuggestionsAdapter.ViewHolder>(DiffCalback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(modification: SuggestionsModels.SuggestionItem) {
            val author = modification.suggestion.author
            view.suggestionAuthorIv.loadAuthorImage(modification.suggestion.author.photoUrl)
            val text = when(modification.suggestion.type) {
                "upd" -> "${author.displayName} has suggestion an update to a note: ${modification.sourceNote?.title}"
                "add" -> "${author.displayName} has suggested a new note called ${modification.suggestion.title}"
                "del" -> "${author.displayName} has suggested to delete a note ${modification.sourceNote?.title}"
                else -> ""
            }

            view.suggestionChageContentTv.setSpannableText(text, author.displayName, modification.sourceNote?.title)

            val background = when {
                modification.rejected -> ContextCompat.getDrawable(view.context, R.drawable.background_shadow_red)
                modification.approved -> ContextCompat.getDrawable(view.context, R.drawable.background_shadow_green)
                else -> null
            }
            view.suggestionChageContentTv.background = background
        }

    }

    class DiffCalback: DiffUtil.ItemCallback<SuggestionsModels.SuggestionItem>() {
        override fun areItemsTheSame(
            oldItem: SuggestionsModels.SuggestionItem,
            newItem: SuggestionsModels.SuggestionItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SuggestionsModels.SuggestionItem,
            newItem: SuggestionsModels.SuggestionItem
        ): Boolean {
            return oldItem == newItem
        }
    }


}