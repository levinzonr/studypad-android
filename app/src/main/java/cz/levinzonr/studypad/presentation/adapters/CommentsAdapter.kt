package cz.levinzonr.studypad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.formatTime
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.item_comment.view.*
import timber.log.Timber

class CommentsAdapter(val listener: CommentsItemListener, val authorId: String? = null) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>(){

    var items : List<PublishedNotebook.Comment> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
    }

    override fun getItemCount(): Int {
    return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bindView(items[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(comment: PublishedNotebook.Comment) {

            view.commentAuthorTv.text = comment.author.displayName
            view.commentContentTv.text = comment.content
            view.commentAuthorIv.loadAuthorImage(comment.author.photoUrl)
            view.commentDateTv.text = comment.dateCreated.formatTime()
            Timber.d("${authorId} == ${comment.author.uuid}")
            view.commentMorBtn.setVisible(authorId == comment.author.uuid)

            view.commentMorBtn.setOnClickListener {
                listener.onCommentMoreButtonPressed(comment)
            }

        }
    }

    interface CommentsItemListener {
        fun onCommentMoreButtonPressed(comment: PublishedNotebook.Comment)
    }

}