package cz.levinzonr.studypad.presentation.screens.challenges


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.ChallengeEntry
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.selectors.NotebookSelectorDialog
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_challenges.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChallengesFragment : BaseFragment(), ChallengesOverviewAdapter.ChallengesListItemListener {

    override val viewModel: ChallengesOverviewViewModel by viewModel()

    private lateinit var adapter: ChallengesOverviewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }


    override fun subscribe() {
        viewModel.recentChallenges.observeNonNull(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            emptyView.setVisible(list.isEmpty())
            sectinName.setVisible(!list.isEmpty())
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyView.configure(R.string.challenges_empty_title, R.string.challenges_empty_message)
        adapter = ChallengesOverviewAdapter()
        challengesRv.adapter = adapter
        adapter.listener = this



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

    override fun onRecentChallengeSelected(challengeEntry: ChallengeEntry) {
        viewModel.onRecentChallengeClicked(challengeEntry)
    }
}
