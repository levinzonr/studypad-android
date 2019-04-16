package cz.levinzonr.studypad.presentation.screens.selectors

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.layoutInflater
import kotlinx.android.synthetic.main.item_notebook_small.view.*

class NotebookSelectionAdapter(private val listener: NotebookSelectionListener): ListAdapter<Notebook, NotebookSelectionAdapter.ViewHolder>(DiffCallback()){

    interface NotebookSelectionListener {
        fun onNotebookSelected(notebook: Notebook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_notebook_small, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(notebook: Notebook) {
            view.isEnabled = notebook.notesCount > 0
            view.notebookNameTv.text = notebook.name
            view.notesCountTv.text = when(notebook.notesCount) {
                1 -> notebook.notesCount.toString() + "1 note"
                else -> "${notebook.notesCount} notes"
            }
            view.setOnClickListener {
                listener.onNotebookSelected(notebook)
            }
        }
    }

    class DiffCallback() : DiffUtil.ItemCallback<Notebook>() {
        override fun areItemsTheSame(oldItem: Notebook, newItem: Notebook): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notebook, newItem: Notebook): Boolean {
            return oldItem == newItem
        }
    }
}