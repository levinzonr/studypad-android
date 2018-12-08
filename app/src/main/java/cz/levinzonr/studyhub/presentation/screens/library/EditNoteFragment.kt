package cz.levinzonr.studyhub.presentation.screens.library


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.Note


class EditNoteFragment : Fragment() {

    val note: Note?
        get() = arguments?.getParcelable("NOTE")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", note.toString())
    }

}
