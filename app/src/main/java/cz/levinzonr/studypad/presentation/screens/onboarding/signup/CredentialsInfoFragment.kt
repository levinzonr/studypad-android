package cz.levinzonr.studypad.presentation.screens.onboarding.signup


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.showAccountCreated
import cz.levinzonr.studypad.presentation.screens.showMain
import kotlinx.android.synthetic.main.fragment_credentials_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CredentialsInfoFragment : BaseFragment() {

    private val viewModel: SignupViewModel by sharedViewModel()

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


    private fun setupListeners() {

        credentialsInfoEmailEt.onTextChanged {
            viewModel.email = it
        }

        credentialsInfoPasswordEt.onTextChanged {
            viewModel.password = it
        }

        credentialsInfoCreateAccount.setOnClickListener {
            viewModel.createAccount()
        }

    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            credentialsInfoPasswordEt.setText(password)
            credentialsInfoEmailEt.setText(email)

            viewModel.accountCreatedSuccessEvent.observe(this@CredentialsInfoFragment, Observer {
                it.handle {
                    if (!it) showMain()
                    else showAccountCreated()
                }
            })

        }
    }
}
