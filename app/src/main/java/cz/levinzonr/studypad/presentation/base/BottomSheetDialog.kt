package cz.levinzonr.studypad.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.models.ViewError
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.common.StudyPadDialog
import timber.log.Timber

abstract class BottomSheetDialog : BottomSheetDialogFragment() {

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewStateObservalbe.observeNonNull(viewLifecycleOwner) { state ->
            Timber.d("Base State: $state")
            showLoading(state.isLoading)
            state.error?.handle { showError(it) }
            state.networkError?.handle { showNetworkUnavailableError() }
        }
    }


    open fun showError(viewError: ViewError) {
        when (viewError) {
            is ViewError.DialogError -> showSimpleDialog(viewError.title, viewError.message)
            is ViewError.ToastError -> showToast(viewError.message)
        }
    }

    open fun showNetworkUnavailableError() {
        showToast(getString(R.string.error_network_message))
    }

    protected open fun showLoading(isLoading: Boolean) {

    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showSimpleDialog(title: String, message: String) {
        StudyPadDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { d -> d.dismiss() }
            .show()
    }



}