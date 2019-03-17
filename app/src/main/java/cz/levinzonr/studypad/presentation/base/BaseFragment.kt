package cz.levinzonr.studypad.presentation.base

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import cz.levinzonr.studypad.presentation.screens.Flow
import cz.levinzonr.studypad.presentation.screens.MainActivity
import cz.levinzonr.studypad.presentation.screens.NavigationEvent
import cz.levinzonr.studypad.presentation.screens.onboarding.OnboardingActivity
import org.koin.android.ext.android.get

abstract class BaseFragment : Fragment() {

    protected fun showToast(message: String) {
        context?.let { Toast.makeText(it, message, Toast.LENGTH_LONG).show() }
    }


    abstract val viewModel: BaseViewModel

    protected fun showErrorDialog(title: String = "error", message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) {_, _ -> }
            .show()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigationLiveData.observe(viewLifecycleOwner, Observer {
            it.handle(this::handleNavigationEvent)
        })
    }
    protected fun shareMessage(message: String) {
        ShareCompat.IntentBuilder.from(activity)
            .setText(message)
            .setType("*/*")
            .startChooser()
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