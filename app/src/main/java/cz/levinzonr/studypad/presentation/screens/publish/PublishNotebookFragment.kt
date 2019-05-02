package cz.levinzonr.studypad.presentation.screens.publish


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.*
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.presentation.base.BackButtonHandler
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.publish.steps.*
import cz.levinzonr.studypad.presentation.screens.selectors.language.LanguageSelectorDialog
import cz.levinzonr.studypad.presentation.screens.selectors.tag.TagSearchDialog
import cz.levinzonr.studypad.presentation.screens.selectors.topic.TopicSearchDialog
import cz.levinzonr.studypad.presentation.screens.selectors.university.UniversitySelectorViewModel
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Exception

class PublishNotebookFragment : BaseFragment(), StepperFormListener, BaseStep.StepViewClickListener, BackButtonHandler {

    private val args: PublishNotebookFragmentArgs by navArgs()
    override val viewModel: PublishNotebookViewModel by viewModel { parametersOf(args.notebook, args.publishedId) }
    private val universitySelectorViewModel: UniversitySelectorViewModel by sharedViewModel()

    private lateinit var stepOne: BasicStep
    private lateinit var stepTwo: AdditionalInfoStep
    private lateinit var stepThree: DescriptionStep
    private lateinit var stepFour: ConfirmationStep


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_notebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stepOne = get { parametersOf(this) }
        stepTwo = get { parametersOf(this) }
        stepThree = get { parametersOf(this) }
        stepFour = get { parametersOf(this) }

        stepperForm
            .setup(this, listOf(stepOne, stepTwo, stepThree, stepFour))
            .displayStepButtons(false)
            .displayBottomNavigation(false)
            .includeConfirmationStep(false)
            .init()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun subscribe() {
        viewModel.defaultStateObservable.observeNonNull(viewLifecycleOwner) { state ->
            state.stepOneDefaults.let(stepOne::setDefaultData)
            state.stepTwoDefaults?.let(stepTwo::setDefaultData)
            state.stepThreeDefaults?.let(stepThree::setDefaultData)
        }

        universitySelectorViewModel.universitySelectedEvent.observeNonNull(viewLifecycleOwner) {
            it?.handle { stepOne.setUniversity(it, true) }
        }
    }


    override fun showLoading(isLoading: Boolean) {
        progressDialog?.getMessageTextView()?.setText(R.string.progress_publishing)
        if (isLoading) progressDialog?.show() else progressDialog?.dismiss()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.notebookTopicEt -> {
                showTopicSelector()
            }
            R.id.notebookLanguageEt -> {
                showLanguageSelector()
            }
            R.id.notebookSchoolEt -> {
                showUniversitySelector()
            }
            else -> {
                showTagSelector()
            }

        }
    }


    override fun showError(viewError: ViewError) {
        super.showError(viewError)
        stepperForm.cancelFormCompletionOrCancellationAttempt()
    }

    override fun showNetworkUnavailableError() {
        showSimpleDialog(getString(R.string.error_network_title), getString(R.string.error_network_message))
        stepperForm.cancelFormCompletionOrCancellationAttempt()
    }

    override fun onCompletedForm() {
        viewModel.publish(stepOne.stepData, stepTwo.stepData, stepThree.stepData)
    }

    override fun onCancelledForm() {

    }

    private fun showTagSelector() {
        TagSearchDialog.show(childFragmentManager, stepTwo.stepData.tags.toSet()) { value, enable ->
            stepTwo.toggleTag(value, enable)
        }
    }

    private fun showUniversitySelector() {
        viewModel.onSelectUniversityClicked()
    }

    private fun showTopicSelector() {
        TopicSearchDialog.show(childFragmentManager) {
            stepTwo.setTopic(it)
        }
    }

    private fun showLanguageSelector() {
        LanguageSelectorDialog.show(childFragmentManager) {
            stepOne.setLanguage(it)
        }
    }

    override fun handleBackButton() {
        if (stepperForm.openStepPosition == 0) {
            baseActivity?.navigateBack()
        } else
            stepperForm.goToPreviousStep(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stepperForm.cancelForm()
    }
}

