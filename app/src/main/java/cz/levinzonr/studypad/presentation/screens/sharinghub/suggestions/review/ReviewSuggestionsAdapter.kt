package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.review

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.modType
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels
import kotlinx.android.synthetic.main.include_note.view.*
import kotlinx.android.synthetic.main.item_suggestion_updated.view.*

class ReviewSuggestionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<SuggestionsModels.SuggestionItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ReviewSuggestionsListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_UPDATED) {
            val view = parent.context.layoutInflater.inflate(R.layout.item_suggestion_updated, parent, false)
            UpdatedViewHolder(view)
        } else {
            val view = parent.context.layoutInflater.inflate(R.layout.item_suggestion_added, parent, false)
            AddedViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddedViewHolder -> holder.bindView(items[position])
            is UpdatedViewHolder -> holder.bindView(items[position])

        }
    }


    override fun getItemCount(): Int {
        return items.count()
    }

    class DiffCalback : DiffUtil.ItemCallback<SuggestionsModels.SuggestionItem>() {
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
            return newItem == oldItem
        }
    }

    inner class AddedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(suggestionItem: SuggestionsModels.SuggestionItem) {
            val author =
                view.context.getString(R.string.suggestion_author, suggestionItem.suggestion.author.displayName)
            view.suggestionTitle.text = view.context.getString(R.string.suggestion_type_add, author)
            view.noteTitleTv.text = suggestionItem.suggestion.title
            view.noteContentTv.text = suggestionItem.suggestion.content
            view.approveBtn.setOnClickListener { listener?.onApproveButtonClicked(suggestionItem) }
            view.rejectedBtn.setOnClickListener { listener?.onRejectButtonClicked(suggestionItem) }

            val backgroundRes = when {
                suggestionItem.approved -> R.drawable.background_shadow_green
                suggestionItem.rejected -> R.drawable.background_shadow_red
                else -> 0
            }

            view.root.setBackgroundResource(backgroundRes)
        }
    }

    inner class UpdatedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(suggestionItem: SuggestionsModels.SuggestionItem) {

            val author =
                view.context.getString(R.string.suggestion_author, suggestionItem.suggestion.author.displayName)
            view.noteTitleTv.text = suggestionItem.suggestion.title
            view.noteContentTv.text = suggestionItem.suggestion.content
            view.noteOldTitleTv.text = suggestionItem.sourceNote?.title
            view.noteOldContentTv.text = suggestionItem.sourceNote?.content
            view.suggestionTitle.text = view.context.getString(R.string.suggestion_type_upd, author)


            view.approveBtn.setOnClickListener { listener?.onApproveButtonClicked(suggestionItem) }
            view.rejectedBtn.setOnClickListener { listener?.onRejectButtonClicked(suggestionItem) }

            val backgroundRes = when {
                suggestionItem.approved -> R.drawable.background_shadow_green
                suggestionItem.rejected -> R.drawable.background_shadow_red
                else -> 0
            }

            view.root.setBackgroundResource(backgroundRes)

        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].suggestion.type.modType == PublishedNotebook.ModificationType.Updated)
            TYPE_UPDATED else TYPE_ADDED
    }

    companion object {
        private const val TYPE_ADDED = 1
        private const val TYPE_UPDATED = 2
    }

    interface ReviewSuggestionsListener {
        fun onRejectButtonClicked(suggestionItem: SuggestionsModels.SuggestionItem)
        fun onApproveButtonClicked(suggestionItem: SuggestionsModels.SuggestionItem)
    }
}