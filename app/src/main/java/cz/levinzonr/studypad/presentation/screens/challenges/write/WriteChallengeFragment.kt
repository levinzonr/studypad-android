package cz.levinzonr.studypad.presentation.screens.challenges.write


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.challenge.ChallengeViewModel
import kotlinx.android.synthetic.main.fragment_write_challenge.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class WriteChallengeFragment : BaseFragment() {

    override val viewModel: ChallengeViewModel<String> by sharedViewModel()


    private val handler = Handler()


    private val list: List<ChallengesModels.NoteItem> by lazy {
        arguments?.getParcelableArrayList<ChallengesModels.NoteItem>(ARG_NOTES)
            ?: arrayListOf<ChallengesModels.NoteItem>()
    }

    private lateinit var adapter: WriteChallengeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupListeners()
    }

    private fun setupListeners() {
        submitAnswerButton.setOnClickListener {
            viewModel.submitAnswer(editText.text.toString())
        }

        skipButton.setOnClickListener {
        }
    }


    private fun setupViewPager() {
        adapter = WriteChallengeAdapter()
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        adapter.submitList(list)
    }

    override fun subscribe() {
        viewModel.viewState.observeNonNull(viewLifecycleOwner) { state ->
            Timber.d("State: $state")
            state.answeredWrong?.handle(this::handleWrongAnswer)
            state.answeredCorrectly?.handle(this::handleCorrectAnswer)
        }
    }

    private fun handleCorrectAnswer(position: Int) {
        adapter.flipToResult(viewPager.currentItem, true)
        handler.postDelayed({
            editText.text?.clear()
            viewPager.currentItem = position
        }, DELAY)

    }

    private fun handleWrongAnswer(position: Int) {
        adapter.flipToResult(viewPager.currentItem, false)
        handler.postDelayed({
            editText.text?.clear()
            textInputLayout.error = "That's wrong, copy the answer"
            adapter.flip(viewPager.currentItem)
        }, DELAY)
    }



    companion object {
        private const val ARG_NOTES = "arg::notes"
        private const val DELAY = 1500L
        fun newInstance(list: List<ChallengesModels.NoteItem>): WriteChallengeFragment {
            return WriteChallengeFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_NOTES, ArrayList(list))
                }
            }
        }
    }
}
