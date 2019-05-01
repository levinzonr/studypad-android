package cz.levinzonr.studypad.presentation.screens.selectors.topic

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.item_topic_selection.view.*

class TopicsSelectionAdapter(private val listener: TopicSelectionListener) : ListAdapter<Topic, TopicsSelectionAdapter.ViewHolder>(
    DiffCallback()
) {

    var selected: ArrayList<Topic> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_topic_selection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(topic: Topic) {
            view.topicNameTv.text = topic.name
            if (selected.contains(topic)) {
                view.tickIv.setVisible(true)
                view.setOnClickListener {
                    selected.remove(topic)
                    listener.onTopicSelectionChanged(selected)
                    notifyDataSetChanged()
                }
            } else {
                view.tickIv.setVisible(false)
                view.setOnClickListener {
                    selected.add(topic)
                    listener.onTopicSelectionChanged(selected)
                    notifyDataSetChanged()

                }
            }
        }
    }

    fun setSelected(list: List<Topic>) {
        selected.clear()
        selected.addAll(list)
    }

    class DiffCallback : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.name == newItem.name
        }
    }

    interface TopicSelectionListener {
        fun onTopicSelectionChanged(selected: List<Topic>)
    }
}