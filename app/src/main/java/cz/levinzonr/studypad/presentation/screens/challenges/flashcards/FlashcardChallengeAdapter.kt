package cz.levinzonr.studypad.presentation.screens.challenges.flashcards

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.getQuantityString
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import kotlinx.android.synthetic.main.item_flashcard_end.view.*
import kotlinx.android.synthetic.main.item_flashcard_note.view.*
import timber.log.Timber

class FlashcardChallengeAdapter(private val listener: LearningChallengeAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<ChallengesModels.NoteItem> = listOf()
        set(value)  {
            field = value
            notifyDataSetChanged()
        }

    private var revealed: ArrayList<ChallengesModels.NoteItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.context.layoutInflater
        return when (viewType) {
            TYPE_COMPLETED -> {
                val view = inflater.inflate(R.layout.item_flashcard_end, parent, false)
                CompleteViewHolder(view)
            }
             else -> {
                 val view = inflater.inflate(R.layout.item_flashcard_note, parent, false)
                 ViewHolder(view)
             }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> holder.bindView(items.get(position))
            is CompleteViewHolder -> holder.bindView()
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(noteItem: ChallengesModels.NoteItem) {
            Timber.d("Bind View: $noteItem")
            view.questionText.text = noteItem.question
            view.answerText.text = noteItem.answer

            if (revealed.contains(noteItem)) {
                Timber.d("Show View 1 : $noteItem")
                view.flipView.showChild(1, false)
            } else {
                Timber.d("Show View 2 : $noteItem")
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


    override fun getItemCount(): Int {
        return items.count() + 1
    }

    inner class CompleteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView() {
            view.challengeCompleteStatTv.text = view.context.getQuantityString(R.plurals.challenge_completed_flashcards_message, items.count())
            view.challengeCompleteRepeatBtn.setOnClickListener { listener.onRepeatChallengeClicked() }
            view.challengeCompleteExitBtn.setOnClickListener { listener.onExitChallengeClicked() }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= items.count())  TYPE_COMPLETED else TYPE_QUESTION
    }


    interface LearningChallengeAdapterListener {
        fun onRevealAnswerClicked(noteItem: ChallengesModels.NoteItem)
        fun onRepeatChallengeClicked()
        fun onExitChallengeClicked()
    }

    companion object {
        private const val TYPE_QUESTION = 1
        private const val TYPE_COMPLETED = 2
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


