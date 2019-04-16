package cz.levinzonr.studypad.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.item_pending_suggestion.view.*

class ReviewSuggestionsPagerAdapter(private val items: List<SuggestionsModels.SuggestionItem>) : PagerAdapter() {

    var listener: ReviewSuggestionsListener? = null

    private val approved: ArrayList<SuggestionsModels.SuggestionItem> = arrayListOf()
    private val rejected: ArrayList<SuggestionsModels.SuggestionItem> = arrayListOf()


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = container.context.layoutInflater.inflate(R.layout.item_pending_suggestion, container, false)
        container.addView(view)
        bindView(view, items[position])
        return view
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


        fun bindView(view: View, suggestionItem: SuggestionsModels.SuggestionItem) {

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
                approved.contains(suggestionItem) -> {
                    view.statusTv.setVisible(true)
                    view.statusTv.text =  "Approved"
                    view.statusTv.setBackgroundColor(ContextCompat.getColor(view.context, R.color.green))

                }
                rejected.contains(suggestionItem) ->  {
                    view.statusTv.setVisible(true)
                    view.statusTv.text = "Rejected"
                    view.statusTv.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                }
                else -> view.statusTv.setVisible(false)
            }


            view.approveBtn.setOnClickListener { approveSuggestion(suggestionItem) }
            view.rejectedBtn.setOnClickListener { rejectSuggestion(suggestionItem) }

        }

        private fun rejectSuggestion(suggestionItem: SuggestionsModels.SuggestionItem) {
            if (rejected.contains(suggestionItem)) return
            if (approved.contains(suggestionItem)) {
                approved.remove(suggestionItem)
            }
            rejected.add(suggestionItem)
            notifyDataSetChanged()
            listener?.onReviewProgressChanged(approved, rejected, count)
        }

        private fun approveSuggestion(suggestionItem: SuggestionsModels.SuggestionItem) {
            if (approved.contains(suggestionItem)) return
            if (rejected.contains(suggestionItem)) {
                rejected.remove(suggestionItem)
            }
            approved.add(suggestionItem)
            notifyDataSetChanged()
            listener?.onReviewProgressChanged(approved, rejected, count)

    }


    interface ReviewSuggestionsListener {
        fun onReviewProgressChanged(approved: List<SuggestionsModels.SuggestionItem>, rejected: List<SuggestionsModels.SuggestionItem>, total: Int)
    }
}