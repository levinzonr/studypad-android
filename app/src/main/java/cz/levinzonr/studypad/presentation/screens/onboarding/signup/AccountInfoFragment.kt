package cz.levinzonr.studypad.presentation.screens.onboarding.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.showAccounCreationNextStep
import cz.levinzonr.studypad.presentation.screens.showUniversitySelector
import kotlinx.android.synthetic.main.fragment_account_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AccountInfoFragment : BaseFragment() {

    private val viewModel: SignupViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            accountInfoLastNameEt.setText(lastName)
            accountInfoFirstNameEt.setText(firstName)
            accountInfoUniversityEt.setText(university?.fullName)
        }
    }

    private fun setupListeners() {
        accountInfoProceedBtn.setOnClickListener {
            showAccounCreationNextStep()
        }

        accountInfoUniversityEt.setOnClickListener {
            showUniversitySelector()
        }


        accountInfoFirstNameEt.onTextChanged {
            viewModel.firstName = it
        }

        accountInfoLastNameEt.onTextChanged {
            viewModel.lastName = it
        }

    }

}
