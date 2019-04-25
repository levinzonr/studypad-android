package cz.levinzonr.studypad.presentation.screens.sharinghub.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.layoutInflater
import kotlinx.android.synthetic.main.item_note_preview.view.*
import kotlinx.android.synthetic.main.item_show_all.view.*

class NotePreviewAdapter(val notes: List<Note>, val listener: NotePreviewListener) :
    RecyclerView.Adapter<ViewHolder>() {


    private var expanded: Boolean = notes.size < MAX_ITEMS
        set(value) {
            field = value
            notifyItemRangeChanged(MAX_ITEMS, notes.count())
        }


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
        return if (expanded) notes.count() else MAX_ITEMS
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) holder.bindView(notes[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(note: Note) {
            view.noteTitleTv.text = note.title
            view.noteContentTv.text = note.content
            view.setOnClickListener { listener.onNotePreviewClicked(note) }
        }
    }

    inner class ShowAllViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.showAllButton.setOnClickListener {
                expanded = !expanded
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!expanded && position == MAX_ITEMS - 1) TYPE_LAST else TYPE_GENERIC
    }

    companion object {
        private const val TYPE_GENERIC = 1
        private const val TYPE_LAST = 2
        private const val MAX_ITEMS = 5
    }

    interface NotePreviewListener {
        fun onShowAllButtonClicked()
        fun onNotePreviewClicked(note: Note)

    }
}