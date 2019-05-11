package cz.levinzonr.studypad.presentation.screens.challenges.flashcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.challenge.ChallengeViewModel
import kotlinx.android.synthetic.main.fragment_flashcard_challenge.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class FlashcardChallengeFragment : BaseFragment(), FlashcardChallengeAdapter.LearningChallengeAdapterListener {

    private val adapter: FlashcardChallengeAdapter by inject { parametersOf(this) }

    override val viewModel: ChallengeViewModel<*> by sharedViewModel()

    private val questions: List<ChallengesModels.NoteItem> by lazy {
       val array = arguments?.getParcelableArrayList<ChallengesModels.NoteItem>(ARG_QUESTIONS)
        return@lazy array ?: listOf<ChallengesModels.NoteItem>()
    }



    private val pageCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Timber.d("$position/${adapter.itemCount}")
            if (position == adapter.itemCount - 1) {
                Timber.d("on complete")
                viewModel.handleChallengeFinish()
            }
        }

    }


    override fun subscribe() {


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

        viewPager.registerOnPageChangeCallback(pageCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(pageCallback)
    }
    override fun onRevealAnswerClicked(noteItem: ChallengesModels.NoteItem) {
    }

    override fun onRepeatChallengeClicked() {
        viewModel.repeatChallenge()
    }

    override fun onExitChallengeClicked() {
        activity?.onBackPressed()
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
