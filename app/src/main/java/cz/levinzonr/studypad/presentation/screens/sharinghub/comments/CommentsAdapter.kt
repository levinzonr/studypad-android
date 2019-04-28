package cz.levinzonr.studypad.presentation.screens.sharinghub.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import kotlinx.android.synthetic.main.item_comment.view.*
import timber.log.Timber

class CommentsAdapter(val listener: CommentsItemListener, val authorId: String? = null) : ListAdapter<PublishedNotebook.Comment, CommentsAdapter.ViewHolder>(
    DiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }



    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(comment: PublishedNotebook.Comment) {

            view.commentAuthorTv.text = comment.author.displayName
            view.commentContentTv.text = comment.content
            view.commentAuthorIv.loadAuthorImage(comment.author.photoUrl)
            view.commentDateTv.text = comment.dateCreated.formatTime(view.context)
            Timber.d("${authorId} == ${comment.author.uuid}")
            view.commentMorBtn.setVisible(authorId == comment.author.uuid)

            if (comment.edited) view.commentDateTv.text = "${view.commentDateTv.text} • edited"

            view.commentMorBtn.setOnClickListener {
                listener.onCommentMoreButtonPressed(comment)
            }

        }
    }


    class DiffCallback : DiffUtil.ItemCallback<PublishedNotebook.Comment>() {
        override fun areItemsTheSame(oldItem: PublishedNotebook.Comment, newItem: PublishedNotebook.Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PublishedNotebook.Comment, newItem: PublishedNotebook.Comment): Boolean {
            return oldItem.content == newItem.content
        }
    }

    interface CommentsItemListener {
        fun onCommentMoreButtonPressed(comment: PublishedNotebook.Comment)
    }
}