package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.baseActivity
import cz.levinzonr.studypad.presentation.adapters.UniversityAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_university_selector.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class UniversitySelectorFragment : BaseFragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {


    override val viewModel: SignupViewModel by sharedViewModel()

    private val adapter: UniversityAdapter by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_university_selector, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        universitiesRv.layoutManager = LinearLayoutManager(context)
        universitiesRv.adapter = adapter
        universitiesRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.onUniversitySelected = {
            viewModel.updateUniversity(it)
        }

        viewModel.universitiesLiveData.observe(this, Observer {
            adapter.items = it
        })

        searchView.setOnQueryTextListener(this)


    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        viewModel.findUnversities(p0 ?: "")
        return true
    }
}
