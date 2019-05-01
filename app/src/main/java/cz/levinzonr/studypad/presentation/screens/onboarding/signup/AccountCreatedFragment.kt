package cz.levinzonr.studypad.presentation.screens.onboarding.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.selectors.university.UniversitySelectorFragment
import cz.levinzonr.studypad.presentation.screens.selectors.university.UniversitySelectorViewModel
import kotlinx.android.synthetic.main.fragment_account_created.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AccountCreatedFragment : BaseFragment() {

    override val viewModel: SignupViewModel by sharedViewModel()
    private val univerViewModel: UniversitySelectorViewModel by sharedViewModel()


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
            viewModel.onSelectUniversityClicked()
        }

        newAccountLaterBtn.setOnClickListener {
            viewModel.showMain()
        }

    }


    override fun subscribe() {
        univerViewModel.universitySelectedEvent.observeNonNull(viewLifecycleOwner) {
            it?.handle {
                viewModel.updateUniversity(it)
            }
        }
    }
}
