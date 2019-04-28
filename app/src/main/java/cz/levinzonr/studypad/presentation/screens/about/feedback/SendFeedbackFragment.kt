package cz.levinzonr.studypad.presentation.screens.about.feedback


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.hideKeyboard
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_send_feedback.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedbackSendBtn.setOnClickListener {
            feedbackSendBtn.hideKeyboard()
            viewModel.onSendFeedbackButtonClicked(feedbackEt.text.toString())
        }

        feedbackEt.onTextChanged { viewModel.onMessageChanged(it) }
    }

    override fun subscribe() {
        viewModel.stateLiveData.observeNonNull(viewLifecycleOwner) {
            feedbackSendBtn.isEnabled = it.sendButtonEnabled
            it.feedbackSentEvent?.handle {
                showToast(getString(R.string.about_feedback_success))
                activity?.onBackPressed()
            }
        }

    }
}
