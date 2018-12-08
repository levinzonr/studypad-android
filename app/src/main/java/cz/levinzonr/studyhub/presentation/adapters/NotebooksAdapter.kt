package cz.levinzonr.studyhub.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.Notebook
import kotlinx.android.synthetic.main.item_notebook.view.*

class NotebooksAdapter : RecyclerView.Adapter<NotebooksAdapter.ViewHolder>() {

    var items: List<Notebook> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: NotebookItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notebook, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }


    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(notebook: Notebook) {
            view.notebookNameTv.text = notebook.name
            view.notebookNotesCountTv.text = "Notes count: ${notebook.notesCount}"
            view.setOnClickListener { listener?.onNotebookSelected(notebook) }
        }
    }

    interface NotebookItemListener {
        fun onNotebookSelected(notebook: Notebook)
    }

}