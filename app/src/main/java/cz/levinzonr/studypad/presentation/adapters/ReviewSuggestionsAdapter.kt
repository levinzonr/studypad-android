package cz.levinzonr.studypad.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.item_pending_suggestion.view.*

class ReviewSuggestionsAdapter : RecyclerView.Adapter<ReviewSuggestionsAdapter.ViewHolder>() {

    var items :List<SuggestionsModels.SuggestionItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ReviewSuggestionsListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_pending_suggestion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items.get(position))
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

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(suggestionItem: SuggestionsModels.SuggestionItem) {

            val showComparison = suggestionItem.suggestion.type == "upd"
            view.divider.setVisible(showComparison)
            view.existedNoteView.setVisible(showComparison)
            view.suggestionTypeTv.text = when (suggestionItem.suggestion.type) {
                "upd" -> "Updated note"
                "del" -> "Deleted note"
                "add" -> "New Nore"
                else -> ""
            }

            view.noteTitleTv.text = suggestionItem.suggestion.title
            view.noteContentTv.text = suggestionItem.suggestion.content
            if (suggestionItem.suggestion.type == "del") {
                suggestionItem.sourceNote?.let {
                    view.noteTitleTv.text = it.title
                    view.noteContentTv.text = it.content
            }

            }
            suggestionItem.sourceNote?.let {
                view.existedNoteTitleTv.text = it.title
                view.existedNoteContentTv.text = it.content
            }

            when {
                suggestionItem.approved -> {
                    view.statusTv.setVisible(true)
                    view.statusTv.text =  "Approved"
                    view.statusTv.setBackgroundColor(ContextCompat.getColor(view.context, R.color.green))

                }
                suggestionItem.rejected ->  {
                    view.statusTv.setVisible(true)
                    view.statusTv.text = "Rejected"
                    view.statusTv.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                }
                else -> view.statusTv.setVisible(false)
            }


            view.approveBtn.setOnClickListener { listener?.onApproveButtonClicked(suggestionItem)}
            view.rejectedBtn.setOnClickListener { listener?.onRejectButtonClicked(suggestionItem) }

        }

    }


    interface ReviewSuggestionsListener {
        fun onRejectButtonClicked(suggestionItem: SuggestionsModels.SuggestionItem)
        fun onApproveButtonClicked(suggestionItem: SuggestionsModels.SuggestionItem)
    }
}