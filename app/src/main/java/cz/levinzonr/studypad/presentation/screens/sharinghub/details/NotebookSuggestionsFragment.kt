package cz.levinzonr.studypad.presentation.screens.sharinghub.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.adapters.SuggestionsAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_notebook_suggestions.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotebookSuggestionsFragment : BaseFragment() {

    override val viewModel: NotebookSuggestionsViewModel by viewModel()

    private val adapter: SuggestionsAdapter by inject()
    private val args: NotebookSuggestionsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notebook_suggestions, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suggestionsRv.adapter = adapter
        suggestionsRv.layoutManager = LinearLayoutManager(context)
        adapter.items = args.suggestions.toList()

    }

}
