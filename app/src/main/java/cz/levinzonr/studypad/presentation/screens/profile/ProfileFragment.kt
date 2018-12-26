package cz.levinzonr.studypad.presentation.screens.profile


import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.onboarding.OnboardingActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment() {

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileLogoutBtn.setOnClickListener {
            viewModel.logout()
        }

        viewModel.openLoginEvent.observe(this, Observer {
            it.handle {
                startActivity(Intent(activity, OnboardingActivity::class.java))
                activity?.finish()
            }
        })

        viewModel.profileLiveData.observe(this, Observer {
            profileUserNae.text = "${it.firstName} ${it.lastName}"
            profileUserUni.text = it.university?.fullName ?: ""
        })
    }

}
