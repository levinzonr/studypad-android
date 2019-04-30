package cz.levinzonr.studypad.presentation.screens.sharinghub.suggestions

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cz.levinzonr.studypad.R

class SuggestionsInfoDialog : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  AlertDialog.Builder(context)
            .setView(R.layout.dialog_suggesions_info)
            .create()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogLayoutParams = dialog?.window?.attributes
        dialog.window?.attributes = dialogLayoutParams
        return dialog
    }


    companion object {
        private const val TAG = "tag:suggest"
        fun show(fm: FragmentManager) {
            val dialog = fm.findFragmentByTag(TAG) as? SuggestionsInfoDialog? ?: SuggestionsInfoDialog()
            dialog.show(fm, TAG)
        }
    }
}