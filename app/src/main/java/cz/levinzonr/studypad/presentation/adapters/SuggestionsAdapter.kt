package cz.levinzonr.studypad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.loadAuthorImage
import kotlinx.android.synthetic.main.item_suggestion.view.*

class SuggestionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<PublishedNotebook.Modification> = listOf()
       set(value) {
           field = value
           notifyDataSetChanged()
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindView(items[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(modification: PublishedNotebook.Modification) {
            view.suggestionAuthorIv.loadAuthorImage(modification.author.photoUrl)
            view.suggestionAuthorTv.text = modification.author.displayName
            view.suggestionChangeTv.text = modification.type
            view.suggestionChageContentTv.text = modification.content
            view.suggestionTypeTv.text = modification.type
        }

    }
}