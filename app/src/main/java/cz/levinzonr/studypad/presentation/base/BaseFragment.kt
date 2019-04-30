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

        viewModel.navigationLiveData.observe(viewLifecycleOwner, Observer {
            it.handle(this::handleNavigationEvent)
        })

        viewModel.viewStateObservalbe.observeNonNull(viewLifecycleOwner) { state ->
            Timber.d("Base State: $state")
            showLoading(state.isLoading)
            state.error?.handle { showError(it) }
            state.networkError?.handle { showNetworkUnavailableError() }
        }


        activity?.let {
            progressDialog = ProgressDialog(it)
            progressDialog?.setCancelable(false)
        }

        subscribe()

    }
    protected fun shareMessage(message: String) {
        ShareCompat.IntentBuilder.from(activity)
            .setText(message)
            .setType("*/*")
            .startChooser()
    }

    open fun onLoseFocus() {}

    abstract fun subscribe()


    open fun showError(viewError: ViewError) {
        when(viewError) {
            is ViewError.DialogError -> showSimpleDialog(viewError.title, viewError.message)
            is ViewError.ToastError -> showToast(viewError.message)
        }
    }

    open fun showNetworkUnavailableError() {
       showToast(getString(R.string.error_network_message))
    }

    protected open fun showLoading(isLoading: Boolean) {

    }

    protected fun copyToClipboard(text: String) {
        val label = "StudyPad"
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.primaryClip = clip
        showToast(getString(R.string.default_copied))
    }

    protected fun showSimpleDialog(title: String, message: String) {
        StudyPadDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { d -> d.dismiss() }
            .show()
    }


    private fun handleNavigationEvent(navigationEvent: NavigationEvent) {
        when(navigationEvent) {
            is NavigationEvent.NavigateTo -> view?.findNavController()?.navigate(navigationEvent.directions)
            is NavigationEvent.NavigateBack -> view?.findNavController()?.popBackStack()
            is NavigationEvent.ChangeFlow -> {
                when(navigationEvent.flow) {
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