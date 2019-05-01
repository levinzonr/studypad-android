package cz.levinzonr.studypad.presentation.screens.selectors.topic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Topic
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicsAdapter(val listener: TopicListener) : RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {



    var item: List<Topic> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false))
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(item[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(topic: Topic) {
            view.topicName.text = topic.name
            view.setOnClickListener {
                listener.onTopicSelected(topic)
            }
        }
    }


    interface TopicListener {
        fun onTopicSelected(topic: Topic)
    }
}