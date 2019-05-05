package cz.levinzonr.studypad.presentation.screens.selectors.university

import android.os.Bundle
import android.view.*
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import cz.levinzonr.studypad.presentation.common.ToolbarSpaceDecoration
import kotlinx.android.synthetic.main.fragment_university_selector.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.FrameLayout
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.StudyPadDialog
import kotlinx.android.synthetic.main.view_empty_state.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class UniversitySelectorFragment : BaseFragment() {


    private val args: UniversitySelectorFragmentArgs by navArgs()
    override val viewModel: UniversitySelectorViewModel by sharedViewModel()

    private val adapter: UniversityAdapter by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_university_selector, container, false)
    }


    override fun subscribe() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            it?.let(this::updateViewState)
        })

    }


    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        universitiesRv.layoutManager = LinearLayoutManager(context)
        universitiesRv.adapter = adapter
        universitiesRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        universitiesRv.addItemDecoration(ToolbarSpaceDecoration(64))


        adapter.onUniversitySelected = {
            searchView.hideKeyboard()
            viewModel.onUniversitySelected(it)
        }

        searchView.onQueryTextChanged {
            viewModel.findUnversities(it)
        }

        if (args.allowSuggestion) {

            emptyView.setActionButton(R.string.university_add_action) {
                StudyPadDialog.Builder(context)
                    .setTitle(getString(R.string.university_add_title))
                    .setMessage(getString(R.string.university_add_message))
                    .setInputField(getString(R.string.university_hint)) {
                        viewModel.suggestionUniversity(it)
                    }
                    .show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        searchView.showKeyboard()
    }


    private fun updateViewState(state: UniversitySelectorViewState) {
        universitiesRv.setVisible(state.universities.isNotEmpty())
        emptyView.setVisible(state.universities.isEmpty())
        adapter.submitList(state.universities)
        if (state.empty) {
            emptyView.configure(R.string.university_default_title)
            emptyView.actionButton.setVisible(false)
        } else {
            emptyView.configure(R.string.university_empty_title)
            emptyView.actionButton.setVisible(args.allowSuggestion)
        }


    }
}