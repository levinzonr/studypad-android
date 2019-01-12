package cz.levinzonr.studypad.presentation.screens.library.publish


import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.common.EntryChip
import cz.levinzonr.studypad.presentation.screens.navigateBack
import cz.levinzonr.studypad.presentation.screens.notebook
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
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
            TagSearchDialog.show(fragmentManager!!, viewModel.selectedTags.value ?: setOf()) { tag, selected ->
                viewModel.setTagSelected(tag, selected)
            }
        }

        notebookTopic.setOnClickListener {
            TopicSearchDialog.show(fragmentManager!!) {
                viewModel.topic = it
                notebookTopic.setText(it.name)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            notebookDescriptionEt.setText(description)
            notebookTitleEt.setText(title)
            notebookTopic.setText(topic?.name ?: "")

        }
    }

    private fun addTag(tag: String) {
        val chip = EntryChip(context).apply { text = tag }

        chip.setOnCloseIconClickListener {
            notebookTags.removeView(chip)
            viewModel.setTagSelected(tag, false)
        }

        notebookTags.addView(chip)
    }

    private fun subscribe() {
        publishButton.setOnClickListener {
            viewModel.publish()
        }

        viewModel.notebookPublishedEvent.onHandle(viewLifecycleOwner) {
            navigateBack()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            it.handle { showToast(it) }
        })

        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            progressBar.setVisible(it)
        })

        viewModel.selectedTags.observe(viewLifecycleOwner, Observer {
            Timber.d("Tag: $it")
            notebookTags.asSequence().toList().forEach {
                if (it == notebookAddTagChip) {
                    Timber.d("Slip view")
                } else {
                    notebookTags.removeView(it)
                    Timber.d("remove ${(it as Chip).text}")
                }
            }
            it.forEach { addTag(it) }
        })
    }
}
