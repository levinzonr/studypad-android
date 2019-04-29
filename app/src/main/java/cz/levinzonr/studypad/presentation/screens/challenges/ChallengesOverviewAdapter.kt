package cz.levinzonr.studypad.presentation.screens.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.ChallengeEntry
import kotlinx.android.synthetic.main.item_challenge.view.*

class ChallengesOverviewAdapter() : ListAdapter<ChallengeEntry, ChallengesOverviewAdapter.ViewHolder>(DiffCallback()){

    var listener: ChallengesListItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(challengeEntry: ChallengeEntry) {
            val iconRes = when(challengeEntry.type) {
                ChallengeType.None -> 0
                ChallengeType.Flashcards -> R.drawable.ic_flashcards_24dp
                ChallengeType.Write -> R.drawable.ic_challenges
                ChallengeType.Selfcheck -> R.drawable.ic_done_all_black_24dp
            }
            view.challengeIcon.setImageResource(iconRes)
            view.challengeTv.text = "${challengeEntry.notebook.name} • ${challengeEntry.type.getName(view.context)}"
            view.challengeSubTv.text = challengeEntry.optionsAsString(view.context)
            view.setOnClickListener { listener?.onRecentChallengeSelected(challengeEntry) }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<ChallengeEntry>() {
        override fun areItemsTheSame(oldItem: ChallengeEntry, newItem: ChallengeEntry): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: ChallengeEntry, newItem: ChallengeEntry): Boolean {
            return newItem == oldItem
        }
    }

    interface ChallengesListItemListener {
        fun onRecentChallengeSelected(challengeEntry: ChallengeEntry)
    }
}