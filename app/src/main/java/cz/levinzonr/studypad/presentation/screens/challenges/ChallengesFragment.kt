package cz.levinzonr.studypad.presentation.screens.challenges


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.screens.challenges.setup.SetupChallengeViewState
import cz.levinzonr.studypad.presentation.screens.selectors.NotebookSelectorDialog
import kotlinx.android.synthetic.main.fragment_challenges.*
import timber.log.Timber


class ChallengesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        materialButton2.setOnClickListener {
            findNavController().navigate(ChallengesFragmentDirections.actionChallengesFragmentToSetupChallengeFragment())
        }

        flashcardButton.setOnClickListener {
            NotebookSelectorDialog.show(childFragmentManager) {
                val dist = SetupChallengeViewState(ChallengeType.Learn, true, true, it)
                findNavController().navigate(ChallengesFragmentDirections.actionChallengesFragmentToLearningChallengeFragment(dist))
            }
        }

        selfcheckButton.setOnClickListener {
            NotebookSelectorDialog.show(childFragmentManager) {
                val dist = SetupChallengeViewState(ChallengeType.Selfcheck, true, true, it)
                findNavController().navigate(ChallengesFragmentDirections.actionChallengesFragmentToLearningChallengeFragment(dist))
            }
        }
    }
}
