package cz.levinzonr.studyhub.presentation.screens.library

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.baseActivity
import cz.levinzonr.studyhub.presentation.base.BaseFragment
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
