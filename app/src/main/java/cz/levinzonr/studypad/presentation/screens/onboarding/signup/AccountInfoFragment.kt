package cz.levinzonr.studypad.presentation.screens.onboarding.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_account_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AccountInfoFragment : BaseFragment() {

    override val viewModel: SignupViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            accountInfoLastNameEt.setText(lastName)
            accountInfoFirstNameEt.setText(firstName)
        }
    }

    override fun subscribe() {
        viewModel.validAccountInfoEvent.observe(this, Observer {
            accountInfoProceedBtn.isEnabled = it
        })
    }

    private fun setupListeners() {
        accountInfoProceedBtn.setOnClickListener {
           viewModel.showAccountInfo()
        }

        accountInfoFirstNameEt.onTextChanged {
            viewModel.firstName = it

        }

        accountInfoLastNameEt.onTextChanged {
            viewModel.lastName = it
        }

        backButton.setOnClickListener { viewModel.onBackButtonClicked() }

    }

}
