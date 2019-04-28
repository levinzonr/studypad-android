package cz.levinzonr.studypad.presentation.screens.library.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    var items : List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var listener: NotesItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(note: Note) {
            view.noteTitleTv.text = note.title
            view.noteContentTv.text = note.content
            view.setOnClickListener { listener?.onNoteClicked(note) }
            view.setOnLongClickListener { listener?.onNoteLongClicked(note);true }
        }

    }

    interface NotesItemListener {
        fun onNoteClicked(note: Note)
        fun onNoteLongClicked(note: Note)
    }





}