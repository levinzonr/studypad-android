package cz.levinzonr.studypad.presentation.screens.selectors


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import cz.levinzonr.studypad.presentation.screens.library.notebooks.NotebookListViewModel
import kotlinx.android.synthetic.main.fragment_notebook_selector_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotebookSelectorDialog : BottomSheetDialog(), NotebookSelectionAdapter.NotebookSelectionListener {

    override val viewModel : NotebookListViewModel by viewModel()
    private var onSelected: ((Notebook) -> Unit)? = null
    private lateinit var adapter: NotebookSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notebook_selector_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NotebookSelectionAdapter(this)
        notebooksRv.adapter = adapter
        super.onViewCreated(view, savedInstanceState)
        viewModel.dataSource.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onNotebookSelected(notebook: Notebook) {
        onSelected?.invoke(notebook)
        dismiss()
    }

    companion object {
        private const val TAG = "Adas"

        fun show(fragmentManager: FragmentManager, onSelected: (Notebook) -> Unit) {
            val frag = fragmentManager.findFragmentByTag(TAG) as? NotebookSelectorDialog? ?: NotebookSelectorDialog()
            frag.onSelected = onSelected
            frag.show(fragmentManager, TAG)
        }
    }
}
