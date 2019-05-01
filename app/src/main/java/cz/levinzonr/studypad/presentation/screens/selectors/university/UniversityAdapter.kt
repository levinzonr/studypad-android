package cz.levinzonr.studypad.presentation.screens.selectors.university

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.University
import kotlinx.android.synthetic.main.item_university.view.*

class UniversityAdapter : ListAdapter<University, UniversityAdapter.ViewHolder>(
    TaskDiffCallback()
) {


    var onUniversitySelected: (University) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_university, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
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

    class TaskDiffCallback : DiffUtil.ItemCallback<University>() {

        override fun areItemsTheSame(oldItem: University, newItem: University): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: University, newItem: University): Boolean {
            return oldItem == newItem
        }
    }}