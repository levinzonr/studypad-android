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
import cz.levinzonr.studypad.onQueryTextChanged
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


class UniversitySelectorFragment : BottomSheetDialog() {


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            Timber.d("State: $it")
            it?.let(this::updateViewState)
        })
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



        searchView.onQueryTextChanged {
            viewModel.findUnversities(it)
        }
    }


    private fun updateViewState(state: UniversitySelectorViewState) {
        universitiesRv.setVisible(state.universities.isNotEmpty())
        emptyViewTv.setVisible(!state.universities.isNotEmpty())
        adapter.submitList(state.universities)
        val message = if (state.empty) "Start by typing name of your University" else "Didn't find anything like ${searchView.query}"
        emptyViewTv.text = message

    }


    companion object {
        private const val TAG = "university"
        fun show(fragmentManager: FragmentManager, onSelected: (University) -> Unit) {
            val frag =
                fragmentManager.findFragmentByTag(TAG) as? UniversitySelectorFragment? ?: UniversitySelectorFragment()
            frag.listener = onSelected
            frag.show(fragmentManager, TAG)
        }
    }
}
