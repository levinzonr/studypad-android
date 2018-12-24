package cz.levinzonr.studyhub.presentation.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.onTextChanged
import cz.levinzonr.studyhub.presentation.base.BaseFragment
import cz.levinzonr.studyhub.presentation.screens.showMain
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginPasswordEt.setText(viewModel.password)
        loginEmailEt.setText(viewModel.email)
        setupListeners()
        subscribe()

    }

    private fun subscribe() {
        viewModel.stateLiveDate.observe(this, Observer {
            when (it) {
                LoginViewModel.State.ERROR -> {
                    loginProgress.visibility = View.GONE

                }
                LoginViewModel.State.LOADING -> loginProgress.visibility = View.VISIBLE
                LoginViewModel.State.SUCCESS -> showMain()
            }
        })
    }

    private fun setupListeners() {

        loginEmailEt.onTextChanged {
            viewModel.email = it
        }

        loginPasswordEt.onTextChanged {
            viewModel.password = it
        }

        loginButton.setOnClickListener {
            viewModel.login()
        }

    }



}
