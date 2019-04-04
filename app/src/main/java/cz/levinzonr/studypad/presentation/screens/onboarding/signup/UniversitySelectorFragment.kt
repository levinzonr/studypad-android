package cz.levinzonr.studypad.presentation.screens.onboarding.signup

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.baseActivity
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.adapters.UniversityAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import cz.levinzonr.studypad.presentation.common.ToolbarSpaceDecoration
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_university_selector.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class UniversitySelectorFragment :  BottomSheetDialog(), androidx.appcompat.widget.SearchView.OnQueryTextListener {


    val viewModel: UniversitySelectorViewModel by viewModel()

    private val adapter: UniversityAdapter by inject()

    private var listener: ((University) -> Unit)? = null

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
        universitiesRv.addItemDecoration(ToolbarSpaceDecoration())

        adapter.onUniversitySelected = {
            listener?.invoke(it)
            dismiss()

        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            Timber.d("State: $it")
            it?.let(this::updateViewState)
        })

        searchView.setOnQueryTextListener(this)
    }


    private fun updateViewState(state: UniversitySelectorViewState) {
        if (state.universities.isNotEmpty()) {
            universitiesRv.setVisible(true)
            adapter.submitList(state.universities)
        } else {
            Timber.d("show stater: ${state.empty}")
        }
    }


    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        viewModel.findUnversities(p0 ?: "")
        return true
    }

    companion object {
        private const val TAG = "university"
        fun show(fragmentManager: FragmentManager, onSelected: (University) -> Unit) {
            val frag = fragmentManager.findFragmentByTag(TAG) as? UniversitySelectorFragment? ?: UniversitySelectorFragment()
            frag.listener = onSelected
            frag.show(fragmentManager, TAG)
        }
    }
}
