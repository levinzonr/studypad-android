package cz.levinzonr.studypad.presentation.screens.challenges.setup


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.selectors.NotebookSelectorDialog
import kotlinx.android.synthetic.main.fragment_setup_challenge.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SetupChallengeFragment : BaseFragment() {

    override val viewModel: SetupChallengeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup_challenge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner, Observer {
            updateView(it)
        })
        setupListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupListeners() {
        challengeTypeWriteBtn.setOnClickListener { viewModel.onChallengeTypeSelected(ChallengeType.Write) }
        challengeTypeTrainBtn.setOnClickListener { viewModel.onChallengeTypeSelected(ChallengeType.Selfcheck) }
        challengeTypeLearnBtn.setOnClickListener { viewModel.onChallengeTypeSelected(ChallengeType.Learn) }

        preferenceReordering.setOnClickListener { viewModel.onShuffleModeChange(!preferenceReorderingSwitch.isChecked) }
        preferenceTitle.setOnClickListener { viewModel.onTitleFirstModeChange(!preferenceTitleSwitch.isChecked) }

        challengeStartBtn.setOnClickListener {
            viewModel.onStartChallengeClicked()
        }

        preferenceReorderingSwitch.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP)
                preferenceReordering.performClick()
            true
        }
        preferenceTitleSwitch.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP)
             preferenceTitle.performClick()
            true
        }

        preferenceNotebookSelector.setOnClickListener {
            NotebookSelectorDialog.show(childFragmentManager) {
                viewModel.onNotebookSelected(it)
            }
        }
    }

    private fun updateView(viewState: SetupChallengeViewState) {
        Timber.d("View State: $viewState")
        Timber.d("State ${viewState.currentType} ${viewState.currentType == ChallengeType.Learn}")
        challengeTypeLearnBtn.isChecked = viewState.currentType == ChallengeType.Learn
        challengeTypeTrainBtn.isChecked = viewState.currentType == ChallengeType.Selfcheck
        challengeTypeWriteBtn.isChecked = viewState.currentType == ChallengeType.Write


        viewState.notebook?.let {
            textView8.text = it.name
        }

        preferenceReorderingSwitch.isChecked = viewState.shuffle
        preferenceTitleSwitch.isChecked = viewState.titleFirst

        challengePrefTypeTv.text = when (viewState.currentType) {
            ChallengeType.Learn -> "Walk through your notes one by one and try to learn as much as you can!"
            ChallengeType.Write -> "Best way to words in different language"
            ChallengeType.Selfcheck -> "Check yourself if you every single note in the notebook"
            ChallengeType.None -> "Select challenge to continue"
        }

        preferenceReorderingSwitch.setOnClickListener { preferenceReordering.performClick() }
        preferenceTitleSwitch.setOnClickListener { preferenceTitle.performClick() }

        challengeStartBtn.isEnabled = viewState.currentType != ChallengeType.None &&  viewState.notebook !=  null

    }

}
