package cz.levinzonr.studypad.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.sharinghub.comments.PublishedNotebookCommentsFragment
import cz.levinzonr.studypad.presentation.screens.sharinghub.details.PublishedNotebookDescriptionFragment

class ViewPagerAdapter(val notebookId: String, val feed: PublishedNotebook.Feed? = null, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){

    val fragments: List<BaseFragment> = listOf(
        PublishedNotebookDescriptionFragment.newInstance(notebookId, feed),
        PublishedNotebookCommentsFragment.newInstance(notebookId)
    )

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) "Details" else "Comments"
    }


}