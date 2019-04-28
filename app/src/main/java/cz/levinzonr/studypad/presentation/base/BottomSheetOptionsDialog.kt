package cz.levinzonr.studypad.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.views
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class BottomSheetOptionsDialog : BottomSheetDialogFragment() {

    abstract val layoutResource: Int

    var onOptionSelected: (Int) -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as ViewGroup).views.forEach {
            (it as? MaterialButton)?.setOnClickListener {
                dismiss()
                onOptionSelected.invoke(it.id)
            }
        }
    }


    companion object {
        inline fun<reified T: BottomSheetOptionsDialog> builder() = Builder(T::class)
    }

    class Builder<T: BottomSheetOptionsDialog>(kClass: KClass<T>) {

        companion object {
            private const val TAG = "slet"
        }

        private val dialog by lazy {
            kClass.createInstance()
        }

        fun show(fm: FragmentManager, onSelected: (Int) -> Unit) {
            val dialog = fm.findFragmentByTag(TAG) as? BottomSheetOptionsDialog? ?: dialog
            dialog.onOptionSelected = onSelected
            dialog.show(fm, TAG)
        }

    }

}

class NoteOptionsDialog: BottomSheetOptionsDialog() {
    override val layoutResource: Int
        get() = R.layout.bottom_menu_note
}

class PublishedBookOptionsDialog : BottomSheetOptionsDialog() {
    override val layoutResource: Int
        get() = R.layout.bottom_menu_published
}

