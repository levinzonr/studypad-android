package cz.levinzonr.studypad.presentation.screens.onboarding.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_credentials_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CredentialsInfoFragment : BaseFragment() {

    override val viewModel: SignupViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credentials_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    override fun subscribe() {

        viewModel.credentiasViewState.observeNonNull(viewLifecycleOwner) { state ->
            state.emailInvalid?.handle { credentialsInfoEmailInputLayout.error = it }
            state.passwordInvalid?.handle { credentialsInfoPasswordInputLayout.error = it }
        }

    }


    override fun showLoading(isLoading: Boolean) {
        progressDialog?.getMessageTextView()?.text = getString(R.string.progress_signup)
        if (isLoading) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun setupListeners() {

        credentialsInfoEmailEt.onTextChanged {
            viewModel.email = it
            credentialsInfoEmailInputLayout.error = null
        }

        credentialsInfoPasswordEt.onTextChanged {
            viewModel.password = it
            credentialsInfoPasswordInputLayout.error = null
        }

        credentialsInfoCreateAccount.setOnClickListener {
            viewModel.onCreateAccountButtonClicked()
        }

        backButton.setOnClickListener {
            viewModel.onBackButtonClicked()
        }
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            credentialsInfoPasswordEt.setText(password)
            credentialsInfoEmailEt.setText(email)

        }
    }
}
