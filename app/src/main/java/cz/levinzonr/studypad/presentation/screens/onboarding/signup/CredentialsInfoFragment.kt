package cz.levinzonr.studypad.presentation.screens.onboarding.signup


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment



class CredentialsInfoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credentials_info, container, false)
    }


}
