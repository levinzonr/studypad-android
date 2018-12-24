package cz.levinzonr.studypad.presentation.screens.library.notes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.baseActivity
import cz.levinzonr.studypad.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.note_detail_fragment.*

class NoteDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = NoteDetailFragment()
    }

    private lateinit var viewModel: NoteDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity?.setSupportActionBar(bottomAppBar)
        viewModel = ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)

    }

}
