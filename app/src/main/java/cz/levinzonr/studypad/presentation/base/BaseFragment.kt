package cz.levinzonr.studypad.presentation.base

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.lmntrx.android.library.livin.missme.ProgressDialog
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.screens.Flow
import cz.levinzonr.studypad.presentation.screens.MainActivity
import cz.levinzonr.studypad.presentation.screens.NavigationEvent
import cz.levinzonr.studypad.presentation.screens.onboarding.OnboardingActivity
import timber.log.Timber
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.common.StudyPadDialog

/**
 * Base Fragment for the others to extend
 * It provides number of helper methods to address most common actions like dialogs e.t.c
 */
abstract class BaseFragment : Fragment() {

    protected fun showToast(message: String) {
        context?.let { Toast.makeText(it, message, Toast.LENGTH_LONG).show() }
    }

    protected var progressDialog: ProgressDialog? = null

    abstract val viewModel: BaseViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // Listen for navigation events
        viewModel.navigationLiveData.observeNonNull(viewLifecycleOwner) {
            it.handle(this::handleNavigationEvent)
        }

        // Listen for base view state (errors, loading, bad network)
        viewModel.viewStateObservalbe.observeNonNull(viewLifecycleOwner) { state ->
            Timber.d("Base State: $state")
            showLoading(state.isLoading)
            state.error?.handle { showError(it) }
            state.networkError?.handle { showNetworkUnavailableError() }
        }


        // init progress dialog
        activity?.let {
            progressDialog = ProgressDialog(it)
            progressDialog?.getMessageTextView()?.setText(R.string.progress_default)
            progressDialog?.setCancelable(false)
        }

        // Subscribtion for other fragments
        subscribe()

    }


    /**
     * Helper method to share notebook deeplink
     */
    protected fun shareMessage(name: String, link: String) {
        val shareLinkIntent = Intent()
        shareLinkIntent.action = Intent.ACTION_SEND
        shareLinkIntent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.defautl_share_message, name, link)
        )
        shareLinkIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareLinkIntent, getString(R.string.default_share_with)))

    }

    open fun onLoseFocus() {}


    /**
     * Method where all the other fragments manage their subscription
     */
    abstract fun subscribe()


    /**
     * Default method that will show an error
     * @param viewError specifies the kind of error to show
     */
    open fun showError(viewError: ViewError) {
        when (viewError) {
            is ViewError.DialogError -> showSimpleDialog(viewError.title, viewError.message)
            is ViewError.ToastError -> showToast(viewError.message)
        }
    }

    /**
     * Default implementation of the method that provides the info about bad connectivity
     */
    open fun showNetworkUnavailableError() {
        showToast(getString(R.string.error_network_message))
    }

    /**
     * Default method to reflect the loading state
     */
    protected open fun showLoading(isLoading: Boolean) {

    }

    /**
     * Helper method to copy text to clipboard
     */
    protected fun copyToClipboard(text: String) {
        val label = "StudyPad"
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.primaryClip = clip
        showToast(getString(R.string.default_copied))
    }

    /**
     * Helper method to show simple info dialog
     */
    protected fun showSimpleDialog(title: String, message: String) {
        StudyPadDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { d -> d.dismiss() }
            .show()
    }


    /**
     * Method that handles navigation event
     * @param navigationEvent is action that shoud be performed
     */
    private fun handleNavigationEvent(navigationEvent: NavigationEvent) {
        when (navigationEvent) {

            // Direction is specified
            is NavigationEvent.NavigateTo -> view?.findNavController()?.navigate(navigationEvent.directions)

            // Navigate Back
            is NavigationEvent.NavigateBack -> view?.findNavController()?.popBackStack()

            // Change Flow i.e Onboarding - Main
            is NavigationEvent.ChangeFlow -> {
                when (navigationEvent.flow) {
                    Flow.ONBOARDING -> {
                        val intent = Intent(context, OnboardingActivity::class.java)
                        activity?.finish()
                        startActivity(intent)
                    }
                    Flow.MAIN -> {
                        val intent = Intent(context, MainActivity::class.java)
                        activity?.finish()
                        startActivity(intent)
                    }
                }
            }
        }
    }


}