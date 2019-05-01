package cz.levinzonr.studypad.presentation.screens.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment() {

    override val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun subscribe() {

        viewModel.profileLiveData.observeNonNull(viewLifecycleOwner) {
            profileUserNae.text = "${it.firstName} ${it.lastName}"
            profileUserUni.text = it.university?.fullName ?: ""
            circleImageView.loadAuthorImage(it.photoUrl)

        }
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


    }

}
