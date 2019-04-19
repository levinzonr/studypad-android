package cz.levinzonr.studypad.presentation.screens.profile


import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.onboarding.OnboardingActivity
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversitySelectorFragment
import cz.levinzonr.studypad.storage.TokenRepository
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class ProfileFragment : BaseFragment() {

    override val viewModel: ProfileViewModel by viewModel()
    private val tokenRepo: TokenRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        editProfileBtn.setOnClickListener {
           viewModel.onEditProfileButtonClicked()
        }

        notificationBtn.setOnClickListener {
            viewModel.onNotificationsButtonClicked()
        }

        profileAboutBtn.setOnClickListener { viewModel.onAboutButtonClicked() }

        profileSettingsBtn.setOnClickListener { viewModel.onSettingsButtonClicked() }

        viewModel.profileLiveData.observe(this, Observer {
            profileUserNae.text = "${it.firstName} ${it.lastName}"
            profileUserUni.text = it.university?.fullName ?: ""
            circleImageView.loadAuthorImage(it.photoUrl)

        })
    }

}
