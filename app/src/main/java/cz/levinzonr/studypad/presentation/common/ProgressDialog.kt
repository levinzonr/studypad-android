package cz.levinzonr.studypad.presentation.common

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cz.levinzonr.studypad.R
import kotlinx.android.synthetic.main.dialog_progress.view.*

class ProgressDialog : DialogFragment() {


    companion object {
        private const val TAG = "progress_dialog"
        private const val ARG_TITLE = "title_arg"

        private fun findOrCreate(fm: FragmentManager): ProgressDialog {
            return fm.findFragmentByTag(TAG) as? ProgressDialog? ?: ProgressDialog()
        }

        fun show(fm: FragmentManager, title: String = "Loading...") {
            val frag = findOrCreate(fm).apply {
                arguments = Bundle().apply { putString(ARG_TITLE, title) }
            }
            if (!frag.isAdded) frag.show(fm, TAG)

        }

        fun hide(fm: FragmentManager) {
            val frag = findOrCreate(fm)
            if (frag.isAdded) frag.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false)
        view.progressTitle.text = arguments?.getString(ARG_TITLE) ?: "Loading..."
        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}