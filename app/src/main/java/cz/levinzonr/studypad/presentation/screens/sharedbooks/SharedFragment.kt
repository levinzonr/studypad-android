package cz.levinzonr.studypad.presentation.screens.sharedbooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.baseActivity
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.presentation.adapters.PublishedNotebooksAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.VerticalSpaceItemDecoration
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_shared.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.view_section.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SharedFragment : BaseFragment(), PublishedNotebooksAdapter.PublishedNotebookItemListener {

    override val viewModel : ShareHubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()

    }

    private fun subscribe() {
        viewModel.dataSource.observe(this, Observer {
           it.forEach(this::addSection)
        })

        viewModel.loadingLiveData.observe(this, Observer {
            progressBar.setVisible(it)
        })

        viewModel.errorLiveData.observe(this, Observer {
            it.handle {
                showToast(it)
            }
        })
    }

    private fun addSection(section: SectionResponse) {
        val adapter = PublishedNotebooksAdapter(PublishedNotebooksAdapter.AdapterType.Short)
        val sectionView = LayoutInflater.from(context).inflate(R.layout.view_section, null, false)
        sectionView.sectionName.text = section.title
        sectionView.sectionRv.adapter = adapter
        adapter.listener = this
        adapter.items = section.items
        sectionView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        sectionContainer.addView(sectionView)

    }


    override fun onPublishedNotebookClicked(publishedNotebook: PublishedNotebook.Feed) {
        viewModel.showDetail(publishedNotebook)
    }
}
