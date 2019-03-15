package cz.levinzonr.studypad.presentation.screens.sharedbooks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.presentation.adapters.ViewPagerAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.feedItem
import kotlinx.android.synthetic.main.fragment_published_notebook_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class PublishedNotebookDetailFragment: BaseFragment() {

    private val viewModel: PublishedNotebookDetailViewModel by viewModel { parametersOf(feedItem.id) }
    private val userManager: UserManager by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_published_notebook_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = ViewPagerAdapter(feedItem.id, childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

    }

}
