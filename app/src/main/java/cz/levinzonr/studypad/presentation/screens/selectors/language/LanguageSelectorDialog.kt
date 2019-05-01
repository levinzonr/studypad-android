package cz.levinzonr.studypad.presentation.screens.selectors.language


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.presentation.base.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_language_selector_dialog.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LanguageSelectorDialog : BottomSheetDialog(),
    LanguagesAdapter.LanguageItemListener {

    override val viewModel: LanguageSelectorViewModel by viewModel()
    private val adapter: LanguagesAdapter by inject { parametersOf(this)}

    private var listener: ((Locale) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language_selector_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languagesRv.adapter = adapter
        languagesRv.layoutManager = LinearLayoutManager(context)

        viewModel.languageLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onLanguageSelected(locale: Locale) {
        listener?.invoke(locale)
        dismiss()
    }

    companion object {
        private const val TAG = "locale"
        fun show(fragmentManager: FragmentManager, onSelected: (Locale) -> Unit) {
            val fragment = fragmentManager.findFragmentByTag(TAG) as? LanguageSelectorDialog? ?: LanguageSelectorDialog()
            fragment.listener = onSelected
            fragment.show(fragmentManager,
                TAG
            )
        }
    }

}
