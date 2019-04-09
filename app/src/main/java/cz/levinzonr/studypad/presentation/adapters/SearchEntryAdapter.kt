package cz.levinzonr.studypad.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.SearchEntry
import cz.levinzonr.studypad.layoutInflater
import kotlinx.android.synthetic.main.item_search_entry.view.*

class SearchEntryAdapter(private val listener: SearchEntriesListener): ListAdapter<SearchEntry, SearchEntryAdapter.ViewHolder>(DiffCallback())  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.context.layoutInflater.inflate(R.layout.item_search_entry, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(searchEntry: SearchEntry) {
            view.searchEntryOrder.text = searchEntry.orderBy.name
            searchEntry.tags.map { Chip(view.context).apply { text = it } }.forEach(view.searchEntryTags::addView)
            val queryValue = if (searchEntry.query.isBlank()) "All" else "\"${searchEntry.query}\""
            val topicValue = when(searchEntry.topics.size) {
                0 -> ""
                1 -> searchEntry.topics.first().name
                2 -> searchEntry.topics.first().name + " and 1 more"
                else -> searchEntry.topics.first().name + " and ${searchEntry.topics.size - 1} more"
            }
            val unviersityValue = if (searchEntry.university != null) "@${searchEntry.university.fullName}" else ""
            view.searchEntry.text = "$queryValue $topicValue $unviersityValue"
            view.setOnClickListener { listener.onSearchEntrieClicked(searchEntry) }
        }

    }

    interface SearchEntriesListener {
        fun onSearchEntrieClicked(searchEntry: SearchEntry)
    }

    class DiffCallback : DiffUtil.ItemCallback<SearchEntry>(){
        override fun areItemsTheSame(oldItem: SearchEntry, newItem: SearchEntry): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchEntry, newItem: SearchEntry): Boolean {
            return oldItem == newItem
        }
    }
}