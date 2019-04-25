package cz.levinzonr.studypad.presentation.screens.about.feedback


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SendFeedbackFragment : BaseFragment() {

    override val viewModel: SendFeedbackViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_feedback, container, false)
    }

    override fun subscribe() {
        viewModel.stateLiveData.observeNonNull(viewLifecycleOwner) {
            it.feedbackSentEvent?.handle {
                activity?.onBackPressed()
                showToast("Thanks for the feedback!")
            }

        }
    }
}
