package cz.levinzonr.studypad.presentation.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class ProgressDialog : DialogFragment() {



    companion object {
        private const val TAG = "progress_dialog"

        private fun findOrCreate(fm: FragmentManager) : ProgressDialog {
            return fm.findFragmentByTag(TAG) as? ProgressDialog? ?: ProgressDialog()
        }

        fun show(fm: FragmentManager) {
            findOrCreate(fm).show(fm, TAG)

        }

        fun hide(fm: FragmentManager) {
            val frag = findOrCreate(fm)
            if (frag.isAdded) frag.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       return AlertDialog.Builder(context)
            .setView(ProgressBar(context))
            .setCancelable(false)
            .create()
    }
}