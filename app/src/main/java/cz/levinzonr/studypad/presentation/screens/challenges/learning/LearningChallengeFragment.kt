package cz.levinzonr.studypad.presentation.screens.challenges.learning


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.challenge.ChallengeViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeFragment
import cz.levinzonr.studypad.presentation.screens.challenges.write.WriteChallengeAdapter
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_learning_challenge.*
import kotlinx.android.synthetic.main.item_pending_suggestion.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class LearningChallengeFragment : BaseFragment() {


    override val viewModel: ChallengeViewModel<Boolean> by sharedViewModel()


    private val questions: List<ChallengesModels.NoteItem> by lazy {
        val array = arguments?.getParcelableArrayList<ChallengesModels.NoteItem>(ARG_QUESTIONS)
        return@lazy array ?: listOf<ChallengesModels.NoteItem>()
    }


    private lateinit var adapter: WriteChallengeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupListeners()
        repeatButton.setVisible(true)
        knowButton.setVisible(true)
        continueBtn.setVisible(false)
    }

    private fun setupListeners() {
        knowButton.setOnClickListener { viewModel.submitAnswer(true) }
        repeatButton.setOnClickListener { viewModel.submitAnswer(false) }
        continueBtn.setOnClickListener { viewModel.submitAnswer(true) }

    }

    private fun setupViewPager() {
        adapter = WriteChallengeAdapter()
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        adapter.submitList(questions)


    }

    override fun subscribe() {
        viewModel.viewState.observeNonNull(viewLifecycleOwner) { state ->
            state.answeredCorrectly?.handle(this::handleCorrectAnswer)
            state.answeredWrong?.handle(this::handleWrongAnswer)
        }
    }

    private fun handleCorrectAnswer(position: Int) {
        repeatButton.setVisible(true)
        knowButton.setVisible(true)
        continueBtn.setVisible(false)

        viewPager.currentItem = position
    }

    private fun handleWrongAnswer(position: Int) {
        repeatButton.setVisible(false)
        knowButton.setVisible(false)
        continueBtn.setVisible(true)

        adapter.flip(position)

    }

    companion object {
        private const val ARG_QUESTIONS = "arg_questions"
        fun newInstance(list: List<ChallengesModels.NoteItem>): LearningChallengeFragment {
            return LearningChallengeFragment().apply {
                arguments = Bundle().apply { putParcelableArrayList(ARG_QUESTIONS, ArrayList(list)) }
            }
        }
    }

}
