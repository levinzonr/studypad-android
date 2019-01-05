package cz.levinzonr.studypad.presentation.screens.library.publish


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.notebook
import cz.levinzonr.studypad.setVisible
import kotlinx.android.synthetic.main.fragment_publish_notebook.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PublishNotebookFragment : BaseFragment() {

    private val viewModel: PublishNotebookViewModel by viewModel { parametersOf(notebook) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_notebook, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publishButton.setOnClickListener {
            viewModel.publish()
        }

        viewModel.errorLiveData.observe(this, Observer {
            it.handle { showToast(it) }
        })

        viewModel.loadingLiveData.observe(this, Observer {
            progressBar.setVisible(it)
        })
    }

}
