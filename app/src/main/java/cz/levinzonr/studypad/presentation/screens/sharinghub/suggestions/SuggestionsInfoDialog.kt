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
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.domain.models.modType
import cz.levinzonr.studypad.layoutInflater
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.include_note.view.*
import kotlinx.android.synthetic.main.item_suggestion_updated.view.*
import timber.log.Timber

class SuggestionsInfoDialog : DialogFragment() {

    private val suggestion: SuggestionsModels.SuggestionItem?
        get() = arguments?.getParcelable(TAG)

   /* override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  AlertDialog.Builder(context)
            .setView(buildView(LayoutInflater.from(context)))
            .create()
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        val dialogLayoutParams = dialog?.window?.attributes
        dialog.window?.attributes = dialogLayoutParams
        return dialog
    }
*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  buildView(inflater)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        val dialogLayoutParams = dialog?.window?.attributes
        dialog?.window?.attributes = dialogLayoutParams
    }


    private fun buildView(layoutInflater: LayoutInflater) : View {
        return if (suggestion?.suggestion?.type?.modType == PublishedNotebook.ModificationType.Updated) {
             layoutInflater.inflate(R.layout.item_suggestion_updated, null, false)
                 .bindUpdatedView(suggestion)
        } else {
             layoutInflater.inflate(R.layout.item_suggestion_added, null, false)
                 .bindAddedView(suggestion)
        }
    }

    private fun View.bindAddedView(suggestion: SuggestionsModels.SuggestionItem?) : View {
        val suggestionItem = suggestion ?: return this
        val author = context.getString(R.string.suggestion_author, suggestionItem.suggestion.author.displayName)
        suggestionTitle.text = context.getString(R.string.suggestion_type_add, author)
        noteTitleTv.text = suggestionItem.suggestion.title
        Timber.d("sugg: ${suggestion.suggestion.content}")
        noteContentTv.text = suggestionItem.suggestion.content
        approveBtn.setVisible(false, View.INVISIBLE)
        rejectedBtn.setVisible(false, View.INVISIBLE)
        return this
    }

    private fun View.bindUpdatedView(suggestion: SuggestionsModels.SuggestionItem?) : View {
        val suggestionItem = suggestion ?: return this
        val author = context.getString(R.string.suggestion_author, suggestionItem.suggestion.author.displayName)
        noteTitleTv.text = suggestionItem.suggestion.title
        noteContentTv.text = suggestionItem.suggestion.content
        noteOldTitleTv.text = suggestionItem.sourceNote?.title
        noteOldContentTv.text = suggestionItem.sourceNote?.content
        suggestionTitle.text = context.getString(R.string.suggestion_type_upd, author)
        approveBtn.setVisible(false, View.INVISIBLE)
        rejectedBtn.setVisible(false, View.INVISIBLE)
        return this
    }

    companion object {
        private const val TAG = "tag:suggest"
        fun show(fm: FragmentManager, suggestionItem: SuggestionsModels.SuggestionItem) {
            val dialog = fm.findFragmentByTag(TAG) as? SuggestionsInfoDialog? ?: SuggestionsInfoDialog()
            dialog.arguments = Bundle().apply { putParcelable(TAG, suggestionItem) }
            dialog.show(fm, TAG)
        }
    }
}