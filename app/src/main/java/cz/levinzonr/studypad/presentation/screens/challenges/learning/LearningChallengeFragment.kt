package cz.levinzonr.studypad.presentation.screens.challenges.learning


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearningChallengeFragment : BaseFragment() {

    override val viewModel: LearningChallengeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_challenge, container, false)
    }


}
