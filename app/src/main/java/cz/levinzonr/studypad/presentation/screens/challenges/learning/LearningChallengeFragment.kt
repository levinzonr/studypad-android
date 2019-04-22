package cz.levinzonr.studypad.presentation.screens.challenges.learning


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeFragment
import kotlinx.android.synthetic.main.fragment_learning_challenge.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LearningChallengeFragment : Fragment(), LearningChallengeAdapter.LearningChallengeItemListener {


    private val questions: List<ChallengesModels.NoteItem> by lazy {
        val array = arguments?.getParcelableArrayList<ChallengesModels.NoteItem>(ARG_QUESTIONS)
        return@lazy array ?: listOf<ChallengesModels.NoteItem>()
    }

    private val adapter: LearningChallengeAdapter by inject { parametersOf(this)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_challenge, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        adapter.submitList(questions)
    }

    override fun onKnowButtonPressed(noteItem: ChallengesModels.NoteItem) {
        Toast.makeText(context, "Show Know", Toast.LENGTH_SHORT).show()
        val currentItem = viewPager.currentItem
        if (currentItem + 1 < adapter.itemCount) {
            viewPager.setCurrentItem(currentItem + 1, true)
        } else {

        }
    }

    override fun onRepeatButtonPressed(noteItem: ChallengesModels.NoteItem) {
        val currentItem = viewPager.currentItem
        if (currentItem + 1 < adapter.itemCount) {
            viewPager.setCurrentItem(currentItem + 1, true)
        } else {

        }
    }

    companion object {
        private const val ARG_QUESTIONS = "arg_questions"
        fun newInstance(list: List<ChallengesModels.NoteItem>) : LearningChallengeFragment {
            return LearningChallengeFragment().apply {
                arguments = Bundle().apply { putParcelableArrayList(ARG_QUESTIONS, ArrayList(list)) }
            }
        }
    }

}
