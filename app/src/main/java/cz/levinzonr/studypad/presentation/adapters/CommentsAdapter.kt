package cz.levinzonr.studypad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import kotlinx.android.synthetic.main.item_comment.view.*
import timber.log.Timber

class CommentsAdapter(val listener: CommentsItemListener, val authorId: String? = null) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>(){

    var items : MutableList<PublishedNotebook.Comment> = mutableListOf()
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

    fun updateComment(comment: PublishedNotebook.Comment) {
        items.indexOfFirstOrNull { comment.id == it.id }?.let { index ->
            items[index] = comment
            notifyItemChanged(index)
        }
    }

    fun deleteComment(comment: PublishedNotebook.Comment) {
        items.indexOfFirstOrNull { comment.id == it.id }?.let { index ->
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun addComment(comment: PublishedNotebook.Comment) {
            items.add(0, comment)
            notifyItemInserted(0)

    }

    interface CommentsItemListener {
        fun onCommentMoreButtonPressed(comment: PublishedNotebook.Comment)
    }
}