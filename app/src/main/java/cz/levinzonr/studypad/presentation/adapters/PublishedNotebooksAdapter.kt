package cz.levinzonr.studypad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.loadAuthorImage
import kotlinx.android.synthetic.main.item_published_notebook.view.*

class PublishedNotebooksAdapter : RecyclerView.Adapter<PublishedNotebooksAdapter.ViewHolder>(){

    var items : List<PublishedNotebook.Feed> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: PublishedNotebookItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_published_notebook, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(notebook: PublishedNotebook.Feed) {

            view.notebookTitleTv.text = notebook.title
            view.notebookAuthorTv.text = notebook.author.displayName
            view.notebookDescriptionTv.text = notebook.description
            view.notebookCommentsTv.text = notebook.commentCount.toString()
            view.notebookNotesCountTv.text = notebook.notesCount.toString()
            view.notebookTagsChips.apply {
                removeAllViews()
                notebook.tags.map { Chip(view.context).apply { text = it } }.forEach {
                    addView(it)
                }
            }

            view.notebookAuthorIv.loadAuthorImage(notebook.author.photoUrl)
            view.setOnClickListener { listener?.onPublishedNotebookClicked(notebook) }
        }
    }

    interface PublishedNotebookItemListener {

        fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed)
    }
}