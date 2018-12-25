package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.adapters.UniversityAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.navigateBack
import kotlinx.android.synthetic.main.fragment_university_selector.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class UniversitySelectorFragment : BaseFragment() {


    private val viewModel: SignupViewModel by sharedViewModel()
    private val adapter: UniversityAdapter by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_university_selector, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText.onTextChanged {
            viewModel.findUnversities(it)
        }

        universitiesRv.layoutManager = LinearLayoutManager(context)
        universitiesRv.adapter = adapter
        adapter.onUniversitySelected = {
            viewModel.university = it
            navigateBack()
        }

        viewModel.universitiesLiveData.observe(this, Observer {
            adapter.items = it
        })

    }

}
