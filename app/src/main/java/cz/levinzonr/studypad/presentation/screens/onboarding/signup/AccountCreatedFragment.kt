package cz.levinzonr.studypad.presentation.screens.onboarding.signup


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.showMain
import cz.levinzonr.studypad.presentation.screens.showUniversitySelector
import kotlinx.android.synthetic.main.fragment_account_created.*


class AccountCreatedFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_created, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newAccountChooseUniBtn.setOnClickListener {
            showUniversitySelector()
        }

        newAccountLaterBtn.setOnClickListener {
            showMain()
        }
    }
}
