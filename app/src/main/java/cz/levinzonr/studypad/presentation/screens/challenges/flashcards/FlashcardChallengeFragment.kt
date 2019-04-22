package cz.levinzonr.studypad.presentation.screens.challenges.flashcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import kotlinx.android.synthetic.main.fragment_flashcard_challenge.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class FlashcardChallengeFragment : Fragment(), FlashcardChallengeAdapter.LearningChallengeAdapterListener {

    private val adapter: FlashcardChallengeAdapter by inject { parametersOf(this) }

    private val questions: List<ChallengesModels.NoteItem> by lazy {
       val array = arguments?.getParcelableArrayList<ChallengesModels.NoteItem>(ARG_QUESTIONS)
        return@lazy array ?: listOf<ChallengesModels.NoteItem>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashcard_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = adapter
        adapter.items = questions
    }

    override fun onRevealAnswerClicked(noteItem: ChallengesModels.NoteItem) {

    }

    override fun onRepeatChallengeClicked() {

    }

    override fun onExitChallengeClicked() {

    }

    companion object {
        private const val ARG_QUESTIONS = "arg_questions"
        fun newInstance(list: List<ChallengesModels.NoteItem>) : FlashcardChallengeFragment {
            return FlashcardChallengeFragment().apply {
                arguments = Bundle().apply { putParcelableArrayList(ARG_QUESTIONS, ArrayList(list)) }
            }
        }
    }

}
