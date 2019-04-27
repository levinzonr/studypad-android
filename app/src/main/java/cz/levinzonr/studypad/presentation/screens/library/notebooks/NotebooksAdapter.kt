package cz.levinzonr.studypad.presentation.screens.library.notebooks

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.getQuantityString
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
            view.notebookTitleTv.text = notebook.name
            val sharedStatus = when {
                notebook.publishedNotebookId !=  null && notebook.authoredByMe -> R.plurals.library_notescount_published
                notebook.publishedNotebookId !=  null && !notebook.authoredByMe -> R.plurals.library_notescount_imported
                else -> R.plurals.library_notescount

            }
            view.notebookNotesCountLayout.text = view.context.resources.getQuantityString(sharedStatus, notebook.notesCount, notebook.notesCount)

            val gradient = GradientDrawable(GradientDrawable.Orientation.BL_TR, notebook.color.toIntArray())
            view.notebookColor.background = gradient
            view.setOnClickListener { listener?.onNotebookSelected(notebook) }
            view.notebookMoreBtn.setOnClickListener { listener?.onNotebookMoreClicked(notebook) }
        }
    }

    interface NotebookItemListener {
        fun onNotebookSelected(notebook: Notebook)
        fun onNotebookMoreClicked(notebook: Notebook)
    }

}