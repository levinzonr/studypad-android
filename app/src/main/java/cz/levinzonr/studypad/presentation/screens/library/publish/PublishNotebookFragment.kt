package cz.levinzonr.studypad.presentation.screens.library.publish


import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.onHandle
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.EntryChip
import cz.levinzonr.studypad.presentation.screens.navigateBack
import cz.levinzonr.studypad.presentation.screens.notebook
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.*

class PublishNotebookFragment : BaseFragment() {

    private val viewModel: PublishNotebookViewModel by viewModel { parametersOf(notebook) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_notebook, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setupListeners()
    }

    private fun setupListeners() {
        notebookDescriptionEt.onTextChanged {
            viewModel.description = it
        }

        notebookTitleEt.onTextChanged {
            viewModel.title = it
        }

        notebookAddTagChip.setOnClickListener {
            addTag("TText ${Date().time}")
        }
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            notebookDescriptionEt.setText(description)
            notebookTitleEt.setText(title)
            tags.forEach { addTag(it) }
            Timber.d("Tags: $tags")

        }
    }

    private fun addTag(tag: String) {
        val chip = EntryChip(context).apply { text = tag }

        chip.setOnCloseIconClickListener {
            notebookTags.removeView(chip)
            viewModel.tags.remove(tag)
        }

        notebookTags.addView(chip)
        viewModel.tags.add(tag)
    }

    private fun subscribe() {
        publishButton.setOnClickListener {
            viewModel.publish()
        }

        viewModel.notebookPublishedEvent.onHandle(this) {
            navigateBack()
        }

        viewModel.errorLiveData.observe(this, Observer {
            it.handle { showToast(it) }
        })

        viewModel.loadingLiveData.observe(this, Observer {
            progressBar.setVisible(it)
        })
    }
}
