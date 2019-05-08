package cz.levinzonr.studypad.presentation.screens.selectors.tag


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onQueryTextChanged
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import cz.levinzonr.studypad.setVisible
import cz.levinzonr.studypad.views
import kotlinx.android.synthetic.main.fragment_tag_search_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class TagSearchDialog : BottomSheetDialog(){

    private lateinit var searchView: SearchView
    private lateinit var chipBox: ChipGroup
    private lateinit var recentBox: ChipGroup
    private lateinit var group: LinearLayout
    private var callback: (String, Boolean) -> Unit = {_, _ -> Timber.d("Default callback")}
    private var callback2: (Set<String>) -> Unit = { Timber.d("Default callback")}

    override val viewModel : TagSearchViewModel  by viewModel()


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

        fun show2(fm: FragmentManager, selected: Set<String>, onClicked: (Set<String>) -> Unit) {
            val frag = fm.findFragmentByTag(TAG) as? TagSearchDialog? ?: TagSearchDialog()
            frag.callback2 = onClicked
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
        group = view.findViewById(R.id.sectionLayout)
        recentBox = view.findViewById(R.id.sectionTagsCG)
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTagsObservable().observe(viewLifecycleOwner, Observer {
            addChips(it, chipBox)
        })
        tagsDoneBtn.setOnClickListener {
            dismiss()
        }

        searchView.onQueryTextChanged {
            viewModel.setFindTagsQuery(it)
        }

        viewModel.getResentObservable().observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) {
                  group.setVisible(false)
            } else {
                group.setVisible(true)
                addChips(it, sectionTagsCG)
            }
        })

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            tagsDoneBtn.setVisible(scrollY >= oldScrollY)
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.setVisible(isLoading)
    }

    private fun addChips(chips: Set<String>, chipGroup: ChipGroup) {
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
                   updateChipState()
               }
           }
           chipGroup.addView(chip)

       }
    }


    private fun updateChipState() {
        val chips : List<Chip> = listOf(chipBox, sectionTagsCG)
            .map { it.views }
            .flatten().mapNotNull { it as? Chip? }

        callback2.invoke(chips.filter { it.isChecked }.map { it.text.toString() }.toSet())
    }



}
