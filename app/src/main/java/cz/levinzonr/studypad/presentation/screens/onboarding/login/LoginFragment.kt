package cz.levinzonr.studypad.presentation.screens.onboarding.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.setVisible
import timber.log.Timber


class LoginFragment : BaseFragment() {

    companion object {
        private const val REQUEST_SIGNIN = 12

    }

    override val viewModel: LoginViewModel by viewModel()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("776080318800-3l21qogmj2ir05fvmnqooib4qrs53olk.apps.googleusercontent.com")
        .requestProfile()
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
        Timber.d("onViewCreated")
        loginPasswordEt.setText(viewModel.password)
        loginEmailEt.setText(viewModel.email)
        setupListeners()
        googleClient = GoogleSignIn.getClient(context!!, gso)
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun subscribe() {
        viewModel.validationViewState.observeNonNull(viewLifecycleOwner) {
            it.emailEvent?.handle { loginEmailInputLayout.error = it }
            it.pwdEvent?.handle { loginPasswordInputLayout.error = it }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressDialog?.getMessageTextView()?.text = getString(R.string.progress_login)
        if (isLoading) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun setupListeners() {

        loginEmailEt.onTextChanged {
            viewModel.email = it
            loginEmailInputLayout.error = null
        }

        loginPasswordEt.onTextChanged {
            viewModel.password = it
            loginPasswordInputLayout.error = null
        }

        loginButton.setOnClickListener {
            viewModel.login()
        }

        loginUsingFacebookBtn.setOnClickListener {
            viewModel.loginViaFacebook(this)
        }

        loginUsingGoogle.setOnClickListener {
            startActivityForResult(
                googleClient.signInIntent,
                REQUEST_SIGNIN
            )
        }

        materialButton.setOnClickListener {
           viewModel.startAccountCreation()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("Acitivirt reseult")
        if (requestCode == REQUEST_SIGNIN) {
            Timber.d("Google result")
            viewModel.handleGoogleSigninResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        } else {
            viewModel.handleFacebookLoginResult(requestCode, resultCode, data)

        }
    }


}
