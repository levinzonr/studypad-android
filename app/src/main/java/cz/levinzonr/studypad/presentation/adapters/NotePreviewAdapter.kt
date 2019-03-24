package cz.levinzonr.studypad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.layoutInflater
import kotlinx.android.synthetic.main.item_note_preview.view.*
import kotlinx.android.synthetic.main.item_show_all.view.*

class NotePreviewAdapter(val notes: List<PublishedNotebook.Note>, val listener: NotePreviewListener) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_GENERIC -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_preview, parent, false)
                ViewHolder(view)
            }

            else -> {
                val view = parent.context.layoutInflater.inflate(R.layout.item_show_all, parent, false)
                ShowAllViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return notes.count() + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) holder.bindView(notes[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(note: PublishedNotebook.Note) {
            view.noteTitleTv.text = note.title
        }
    }

    inner class ShowAllViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.showAllButton.setOnClickListener {
                listener?.onShowAllButtonClicked()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < notes.size) TYPE_GENERIC else TYPE_LAST
    }

    companion object {
        private const val TYPE_GENERIC = 1
        private const val TYPE_LAST = 2
    }

    interface NotePreviewListener {
        fun onShowAllButtonClicked()

    }
}