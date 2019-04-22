package cz.levinzonr.studypad.presentation.screens.challenges.challenge


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeFragment
import cz.levinzonr.studypad.presentation.screens.challenges.learning.LearningChallengeFragment
import kotlinx.android.synthetic.main.fragment_challenge.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChallengeFragment : BaseFragment(), BackButtonHandler {


    private val args: ChallengeFragmentArgs by navArgs()

    override val viewModel: ChallengeViewModel by viewModel { parametersOf(args.setup) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.questionsLiveData.observeNonNull(viewLifecycleOwner) {
            showChallenge(args.setup.currentType, it)
        }

        closeChallengeBtn.setOnClickListener {
            handleBackButton()
        }
    }

    private fun showChallenge(type: ChallengeType, questions: List<ChallengesModels.NoteItem>) {

      /*  val fragment = when (type) {
            ChallengeType.Learn ->
            ChallengeType.Selfcheck -> LearningChallengeFragment.newInstance(questions)
            else -> FlashcardChallengeFragment.newInstance(questions)
        }*/

        childFragmentManager.beginTransaction()
            .replace(R.id.container, FlashcardChallengeFragment.newInstance(questions))
            .commit()
    }


    override fun handleBackButton() {
        AlertDialog.Builder(context)
            .setTitle("Leaving test")
            .setMessage("Are you you want to exit this challenge? Progress won't be saved")
            .setPositiveButton(android.R.string.yes) { _, _ -> viewModel.onLeaveChallengeConfirmed() }
            .setNegativeButton(android.R.string.no) { d, _ -> d.dismiss() }
            .show()
    }
}
