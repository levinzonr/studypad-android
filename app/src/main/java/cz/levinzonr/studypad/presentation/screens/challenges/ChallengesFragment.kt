package cz.levinzonr.studypad.presentation.screens.challenges


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.selectors.NotebookSelectorDialog
import kotlinx.android.synthetic.main.fragment_challenges.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChallengesFragment : BaseFragment() {

    override val viewModel: ChallengesOverviewViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChallengesOverviewAdapter()
        challengesRv.adapter = adapter

        viewModel.recentChallenges.observeNonNull(viewLifecycleOwner) {
            adapter.submitList(it.reversed().first(2))
        }


        materialButton2.setOnClickListener {
            viewModel.onConfigureChallengeClicked()
        }

        flashcardButton.setOnClickListener {
            NotebookSelectorDialog.show(childFragmentManager) {
               viewModel.onLuanchQuickChallenge(ChallengeType.Flashcards, it)
            }
        }

        selfcheckButton.setOnClickListener {
            NotebookSelectorDialog.show(childFragmentManager) {
                viewModel.onLuanchQuickChallenge(ChallengeType.Selfcheck, it)
            }
        }

        writeButton.setOnClickListener {
            NotebookSelectorDialog.show(childFragmentManager) {
                viewModel.onLuanchQuickChallenge(ChallengeType.Write, it)
            }
        }
    }
}
