package cz.levinzonr.studypad.presentation.base

import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {



    protected fun showToast(message: String) {
        context?.let { Toast.makeText(it, message, Toast.LENGTH_LONG).show() }
    }


    protected fun showErrorDialog(title: String = "error", message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) {_, _ -> }
            .show()
    }


}