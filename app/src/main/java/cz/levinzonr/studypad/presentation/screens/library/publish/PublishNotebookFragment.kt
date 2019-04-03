package cz.levinzonr.studypad.presentation.screens.library.publish


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.AdditionalInfoStep
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.BaseStep
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.BasicStep
import cz.levinzonr.studypad.presentation.screens.library.publish.steps.DescriptionStep
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PublishNotebookFragment : BaseFragment(), StepperFormListener, BaseStep.StepViewClickListener {

    private val args: PublishNotebookFragmentArgs by navArgs()
    override val viewModel: PublishNotebookViewModel by viewModel { parametersOf(args.notebook) }

    private lateinit var stepOne: BasicStep
    private lateinit var stepTwo: AdditionalInfoStep
    private lateinit var stepThree: DescriptionStep


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_notebook, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stepOne = BasicStep().apply { listener = this@PublishNotebookFragment }
        stepTwo = AdditionalInfoStep().apply { listener = this@PublishNotebookFragment }
        stepThree = DescriptionStep().apply { listener = this@PublishNotebookFragment }

        stepperForm
            .setup(this, listOf(stepOne, stepTwo, stepThree))
            .init()
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.notebookTopicEt -> { showTopicSelector() }
            R.id.notebookLanguageEt -> { showLanguageSelector() }
            R.id.notebookSchoolEt -> {showSchoolSelector() }
            else -> { showTagSelector() }

        }
    }

    override fun onCompletedForm() {
    }

    override fun onCancelledForm() {

    }

    private fun showTagSelector() {
        TagSearchDialog.show(childFragmentManager, stepTwo.stepData.tags.toSet()) { value, enable ->
            stepTwo.toggleTag(value,enable)
        }
    }

    private fun showTopicSelector() {
        TopicSearchDialog.show(childFragmentManager) {
            stepTwo.setTopic(it)
        }
    }

    private fun showSchoolSelector() {

    }

    private fun showLanguageSelector() {
        LanguageSelectorDialog.show(childFragmentManager) {
            stepOne.setLanguage(it)
        }
    }
}

