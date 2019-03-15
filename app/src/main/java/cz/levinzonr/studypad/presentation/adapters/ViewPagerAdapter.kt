package cz.levinzonr.studypad.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.sharedbooks.PublishedNotebookCommentsFragment
import cz.levinzonr.studypad.presentation.screens.sharedbooks.PublishedNotebookDescriptionFragment

class ViewPagerAdapter(val notebookId: String, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){

    private val fragments: List<BaseFragment> = listOf(
        PublishedNotebookDescriptionFragment.newInstance(notebookId),
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