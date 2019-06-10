package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.setSpannableText
import kotlinx.android.synthetic.main.item_suggestion.view.*

class SuggestionsAdapter : ListAdapter<SuggestionsModels.SuggestionItem, SuggestionsAdapter.ViewHolder>(
    DiffCalback()
) {

    var listener: SuggestionItemListener? = null

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
            val modType = PublishedNotebook.ModificationType.from(modification.suggestion.type)
            val text = when(modType) {
                PublishedNotebook.ModificationType.Updated -> view.context.getString(R.string.suggestion_body_update, author.displayName, modification.sourceNote?.title)
                PublishedNotebook.ModificationType.Added ->  view.context.getString(R.string.suggestion_body_add, author.displayName, modification.suggestion.title)
                PublishedNotebook.ModificationType.Deleted -> "${author.displayName} has suggested to delete a note ${modification.sourceNote?.title}"
            }

            view.suggestionChageContentTv.setSpannableText(text, author.displayName, modification.sourceNote?.title, modification.suggestion.title)

            val background = when {
                modification.rejected -> ContextCompat.getDrawable(view.context, R.drawable.background_shadow_red)
                modification.approved -> ContextCompat.getDrawable(view.context, R.drawable.background_shadow_green)
                else -> null
            }
            view.suggestionChageContentTv.background = background
            view.setOnClickListener { listener?.onSuggestionClicked(modification) }
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


    interface SuggestionItemListener {
        fun onSuggestionClicked(suggestionItem: SuggestionsModels.SuggestionItem)
    }

}