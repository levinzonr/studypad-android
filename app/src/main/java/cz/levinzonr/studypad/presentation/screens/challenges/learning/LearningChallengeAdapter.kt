package cz.levinzonr.studypad.presentation.screens.challenges.learning

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeAdapter
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.item_learning_note.view.*

class LearningChallengeAdapter(private val listener: LearningChallengeItemListener) : ListAdapter<ChallengesModels.NoteItem, LearningChallengeAdapter.ViewHolder>(FlashcardChallengeAdapter.DiffCallback()) {

    private val revealed = ArrayList<ChallengesModels.NoteItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_learning_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    interface LearningChallengeItemListener {
        fun onKnowButtonPressed(noteItem: ChallengesModels.NoteItem)
        fun onProceedButton(noteItem: ChallengesModels.NoteItem)
        fun onRepeatButtonPressed(noteItem: ChallengesModels.NoteItem)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(noteItem: ChallengesModels.NoteItem) {

            view.questionText.text = noteItem.question
            view.answerText.text = noteItem.answer


            val contains = revealed.contains(noteItem)
            view.repeatButton.setVisible(!contains)
            view.knowButton.setVisible(!contains)
            view.continueBtn.setVisible(contains)
            if (contains) {
                view.flipView.showChild(1, false)

            } else {
                view.flipView.showChild(0, false)
            }

            view.knowButton.setOnClickListener { listener.onKnowButtonPressed(noteItem) }
            view.repeatButton.setOnClickListener {
                view.flipView.showChild(1, true)
                revealed.add(noteItem)
                val contains = revealed.contains(noteItem)

                view.repeatButton.setVisible(!contains)
                view.knowButton.setVisible(!contains)
                view.continueBtn.setVisible(contains)
                listener.onRepeatButtonPressed(noteItem)
            }
            view.continueBtn.setOnClickListener { listener.onProceedButton(noteItem) }

        }
    }
}