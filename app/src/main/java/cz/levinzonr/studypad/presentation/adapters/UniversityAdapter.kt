package cz.levinzonr.studypad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.University
import kotlinx.android.synthetic.main.item_university.view.*

class UniversityAdapter : RecyclerView.Adapter<UniversityAdapter.ViewHolder>() {


    var onUniversitySelected: (University) -> Unit = {}

    var items : List<University> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(university: University) {

            view.universityNameTv.text = "${university.fullName}"
            view.universityLocationTv.text = university.location.country
            view.setOnClickListener {
                onUniversitySelected(university)
            }

        }

    }
}