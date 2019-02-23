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
import cz.levinzonr.studypad.presentation.screens.showMain
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import cz.levinzonr.studypad.presentation.screens.showAccountCreation
import cz.levinzonr.studypad.presentation.screens.showUniversitySelector
import cz.levinzonr.studypad.setVisible
import org.koin.android.ext.android.inject
import timber.log.Timber


class LoginFragment : BaseFragment() {

    companion object {
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
        Timber.d("onViewCreated")
        loginPasswordEt.setText(viewModel.password)
        loginEmailEt.setText(viewModel.email)
        setupListeners()
        subscribe()
        googleClient = GoogleSignIn.getClient(context!!, gso)
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    private fun subscribe() {
        viewModel.errorLiveData.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                showToast(it)
                Timber.d("Error data: $it")
            }
        })

        viewModel.loadingLiveData.observe(this, Observer {
            loginProgress.setVisible(it)
            Timber.d("Loading: $it")
        })

        viewModel.loginSuccessEvent.observe(this, Observer {
            it.handle {isNewUser ->
                if (isNewUser) showUniversitySelector()
                else showMain()
            }

        })

        viewModel.passwordValidationEvent.observe(this, Observer {
            it.handle {
               loginPasswordInputLayout.error = "Incorrect password"
            }
        })

        viewModel.emailValidationEvent.observe(this, Observer {
            it.handle {
                loginEmailInputLayout.error = "Incorrect email"
            }
        })
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
            showAccountCreation()
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
