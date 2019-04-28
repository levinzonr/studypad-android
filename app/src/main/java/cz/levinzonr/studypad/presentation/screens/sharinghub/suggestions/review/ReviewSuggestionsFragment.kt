package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.review


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import cz.levinzonr.studypad.R
import kotlinx.android.synthetic.main.fragment_review_suggestions.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import cz.levinzonr.studypad.dp
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.StudyPadDialog
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsAdapter
import cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions.SuggestionsModels
import kotlinx.android.synthetic.main.review_status_bottom_sheet.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class ReviewSuggestionsFragment : BaseFragment(),
    ReviewSuggestionsAdapter.ReviewSuggestionsListener {

    private val args: ReviewSuggestionsFragmentArgs by navArgs()
    override val viewModel: ReviewSuggestionsViewModel by viewModel { parametersOf(args.suggestions, args.notes) }
    private lateinit var adapter: ReviewSuggestionsAdapter
    private lateinit var listAdapter: SuggestionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_suggestions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = SuggestionsAdapter()
        adapter = ReviewSuggestionsAdapter()
        suggestionsRv.adapter = listAdapter
        suggestionsRv.addItemDecoration(VerticalSpaceItemDecoration(8.dp))
        progressBar.max = args.suggestions.count()
        progressBar.progress = 1
        viewPager.adapter = adapter
        adapter.listener = this
        val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                progressBar.progress = position + 1
            }
        })

        confirmBtn.setOnClickListener {
            viewModel.onSumbitReviewBtnClicked(args.notebookId)
        }

        sheetBehavior.isHideable = false
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {

            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {

            }
        })



    }


    override fun subscribe() {

        viewModel.suggestionsLiveData.observe(viewLifecycleOwner, Observer {
            updateLists(it.suggestions)
            updateSheet(it.suggestions)
        })

        viewModel.conflictedSuggestion.observe(viewLifecycleOwner, Observer {
            it?.handle(this::showConfictDialog)
        })

    }

    override fun showLoading(isLoading: Boolean) {
        progressDialog?.getMessageTextView()?.setText(R.string.progress_review)
        if (isLoading) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun showConfictDialog(conflict: Conflict) {
        val chosenName = conflict.chosen.suggestion.author.displayName
        val conflictName = conflict.conflicted.suggestion.author.displayName
        val message = getString(R.string.suggestion_conflict_message, chosenName, conflictName)
        StudyPadDialog.Builder(context)
            .setTitle(getString(R.string.suggestion_conflict))
            .setMessage(message)
            .setPositiveButton(getString(android.R.string.ok)) { d -> d.dismiss() }
            .show()
    }

    private fun updateLists(list: List<SuggestionsModels.SuggestionItem>) {
        adapter.items = (list)
        listAdapter.submitList(list)
    }

    private fun updateSheet(list: List<SuggestionsModels.SuggestionItem>) {
        val approved = list.filter { it.approved }.count()
        val rejected = list.filter { it.rejected }.count()
        val remains = list.size - approved - rejected
        reviewStatusTotal.text = getString(R.string.suggestions_review_count, approved + rejected, list.size)
        reviewStatusProgress.text = getString(R.string.suggestions_review_state, approved, rejected)
        confirmBtn.isEnabled = remains != list.count()
        if (remains == 0) {
            val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }


    override fun onRejectButtonClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        viewModel.onRejectSuggestionClicked(suggestionItem)
    }

    override fun onApproveButtonClicked(suggestionItem: SuggestionsModels.SuggestionItem) {
        viewModel.onApproveSuggestionClicked(suggestionItem)

    }
}
