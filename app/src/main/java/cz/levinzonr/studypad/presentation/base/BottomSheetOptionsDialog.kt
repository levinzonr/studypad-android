package cz.levinzonr.studypad.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import cz.levinzonr.studypad.views

abstract class BottomSheetOptionsDialog : BottomSheetDialogFragment() {

    abstract val layoutResource: Int

    var onOptionSelected: (Int) -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view as ViewGroup).views.forEach {
            (it as MaterialButton).setOnClickListener {
                dismiss()
                onOptionSelected.invoke(it.id)
            }
        }
    }


}