package cz.levinzonr.studypad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import kotlinx.android.synthetic.main.item_note_preview.view.*

class NotePreviewAdapter(val notes: List<PublishedNotebook.Note>) : RecyclerView.Adapter<NotePreviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_preview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(notes[position])
    }

    inner class ViewHolder(val view :  View) : RecyclerView.ViewHolder(view) {

        fun bindView(note: PublishedNotebook.Note) {
            view.noteTitleTv.text = note.title
        }
    }
}