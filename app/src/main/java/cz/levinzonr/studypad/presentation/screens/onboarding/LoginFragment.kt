package cz.levinzonr.studypad.presentation.screens.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.showMain
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()

        private const val REQUEST_SIGNIN = 12

    }

    private val viewModel: LoginViewModel by viewModel()

    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    private lateinit var googleClient: GoogleSignInClient


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
        googleClient = GoogleSignIn.getClient(context!!, gso)
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

        loginUsingFacebookBtn.setOnClickListener {
            viewModel.loginViaFacebook(this)
        }

        sign_in_button.setOnClickListener {
            startActivityForResult(googleClient.signInIntent, REQUEST_SIGNIN)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGNIN) {
            viewModel.handleGoogleSigninResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        } else {
            viewModel.handleFacebookLoginResult(requestCode, resultCode, data)

        }
    }



}
