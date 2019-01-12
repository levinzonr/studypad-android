package cz.levinzonr.studypad.presentation.screens.library.publish


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.onQueryTextChanged
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_tag_search_dialog.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class TagSearchDialog : BottomSheetDialog(){

    private lateinit var searchView: SearchView
    private lateinit var chipBox: ChipGroup

    private var callback: (String, Boolean) -> Unit = {_, _ -> Timber.d("Default callback")}

    private val viewModel : TagSearchViewModel  by viewModel()

    private val selectedTags : Set<String>
        get() = (arguments?.getStringArrayList(ARG_SELECTED) ?: ArrayList()).toSet()

    companion object {

        private const val TAG = "tag"
        private const val ARG_SELECTED = "selected_tags"

        fun show(fm: FragmentManager, selected: Set<String>, onClicked: (String, Boolean) -> Unit) {
            val frag = fm.findFragmentByTag(TAG) as? TagSearchDialog? ?: TagSearchDialog()
            frag.callback = onClicked
            val bundle = Bundle().apply {
                putStringArrayList(ARG_SELECTED, ArrayList(selected))
            }
            frag.arguments = bundle
            frag.show(fm, TAG)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tag_search_dialog, container, false)
        searchView = view.findViewById(R.id.tagSearchView)
        chipBox = view.findViewById(R.id.tagsChipBox)
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTagsObservable().observe(this, Observer {
            addChips(it)
        })
        tagsDoneBtn.setOnClickListener {
            dismiss()
        }

        searchView.onQueryTextChanged {
            viewModel.setFindTagsQuery(it)
        }
    }


    private fun addChips(chips: Set<String>) {
        chipBox.removeAllViews()
        Timber.d("Chips: $chips")
       chips.forEach {
           val chip = Chip(context).apply {
               isClickable = true
               isCheckable = true
               text = it
               isChecked = selectedTags.contains(it)
               setOnCheckedChangeListener { chip, selected ->
                    callback.invoke(it, selected)
               }
           }
           chipBox.addView(chip)

       }
    }


}
