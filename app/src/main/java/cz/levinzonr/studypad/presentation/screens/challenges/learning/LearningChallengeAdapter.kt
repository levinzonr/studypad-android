package cz.levinzonr.studypad.presentation.screens.challenges.learning

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import kotlinx.android.synthetic.main.item_learning_note.view.*

class LearningChallengeAdapter(private val listener: LearningChallengeAdapterListener) :
    ListAdapter<ChallengesModels.NoteItem, LearningChallengeAdapter.ViewHolder>(DiffCallback()) {


    private var revealed: ArrayList<ChallengesModels.NoteItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_learning_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(noteItem: ChallengesModels.NoteItem) {
            view.questionText.text = noteItem.question
            view.answerText.text = noteItem.answer

            if (revealed.contains(noteItem)) {
                view.flipView.showChild(1, false)
            } else {
                view.flipView.showChild(0, false)
            }

            view.showAnswerBtn.setOnClickListener {
                if (!revealed.contains(noteItem)) {
                    revealed.add(noteItem)
                    view.flipView.showChild(1, true)
                }
            }
            view.showQuestionBtn.setOnClickListener {
                if (revealed.contains(noteItem)) {
                    revealed.remove(noteItem)
                    view.flipView.showChild(0, true)
                }
            }
        }
    }


    interface LearningChallengeAdapterListener {
        fun onRevealAnswerClicked(noteItem: ChallengesModels.NoteItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<ChallengesModels.NoteItem>() {
        override fun areItemsTheSame(oldItem: ChallengesModels.NoteItem, newItem: ChallengesModels.NoteItem): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: ChallengesModels.NoteItem,
            newItem: ChallengesModels.NoteItem
        ): Boolean {
            return oldItem.showAnswer == newItem.showAnswer
        }
    }
}


