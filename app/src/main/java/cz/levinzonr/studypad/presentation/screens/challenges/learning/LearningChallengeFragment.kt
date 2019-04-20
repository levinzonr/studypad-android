package cz.levinzonr.studypad.presentation.screens.challenges.learning


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import kotlinx.android.synthetic.main.fragment_learning_challenge.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LearningChallengeFragment : BaseFragment(), LearningChallengeAdapter.LearningChallengeAdapterListener, BackButtonHandler {


    private val args: LearningChallengeFragmentArgs by navArgs()

    override val viewModel: LearningChallengeViewModel by viewModel { parametersOf(args.setup) }

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
        viewModel.questionsLiveData.observeNonNull(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onRevealAnswerClicked(noteItem: ChallengesModels.NoteItem) {

    }

    override fun handleBackButton() {
        AlertDialog.Builder(context)
            .setTitle("Leaving test")
            .setMessage("Are you you want to exit this challenge? Progress won't be saved")
            .setPositiveButton(android.R.string.yes) { _, _ ->  viewModel.onLeaveChallengeConfirmed() }
            .setNegativeButton(android.R.string.no) { d, _ -> d.dismiss() }
            .show()
    }
}
