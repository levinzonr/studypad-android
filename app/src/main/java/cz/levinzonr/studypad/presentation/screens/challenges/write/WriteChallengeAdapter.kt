package cz.levinzonr.studypad.presentation.screens.challenges.write

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeAdapter
import kotlinx.android.synthetic.main.item_flashcard_end.view.*
import kotlinx.android.synthetic.main.item_question.view.*
import timber.log.Timber

class WriteChallengeAdapter : ListAdapter<ChallengesModels.NoteItem, WriteChallengeAdapter.ViewHolder>(FlashcardChallengeAdapter.DiffCallback()) {

    private val revealed: ArrayList<ChallengesModels.NoteItem> = arrayListOf()
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    fun flip(position: Int, flipIfRevealed: Boolean = false) {

        val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position) as? ViewHolder?
        viewHolder?.flipView(getItem(position), flipIfRevealed)
    }

    fun flipToResult(position: Int, correct: Boolean) {
        val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position) as? ViewHolder?
        if (correct) viewHolder?.flipToCorrect() else viewHolder?.flipToWrong()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(noteItem: ChallengesModels.NoteItem) {
            Timber.d("Bind View")
            if (revealed.contains(noteItem)) {
                view.flipView.showChild(1, false)
            } else {
                view.flipView.showChild(0, false)
            }
            view.answerText.text = noteItem.answer
            view.questionText.text = noteItem.question
        }


        fun flipView(noteItem: ChallengesModels.NoteItem, flipIfRevealed: Boolean) {
            Timber.d("flipView")
            val flipped = revealed.contains(noteItem)
            if (flipped && !flipIfRevealed) {
                if (view.flipView.visibleChild != 1) view.flipView.showChild(1, true)
                return
            }
            if (flipped) {
                revealed.remove(noteItem)
                view.flipView.showChild(0, true)
            } else {
                revealed.add(noteItem)
                view.flipView.showChild(1, true)
            }
        }

        fun flipToCorrect() {
            val color = ContextCompat.getColor(view.context, R.color.green)
            view.emptyView.configure("Well done!", "Get ready for the next question", R.drawable.baseline_check_circle_outline_black_48)
            view.emptyView.imageView.imageTintList = ColorStateList.valueOf(color)
            view.flipView.showChild(2, true)

        }

        fun flipToWrong() {
            val color = ContextCompat.getColor(view.context, R.color.red)
            view.emptyView.configure("Note quite...", imageView =  R.drawable.ic_highlight_off_black_24dp)
            view.emptyView.imageView.imageTintList = ColorStateList.valueOf(color)
            view.flipView.showChild(2, true)

        }



    }



}