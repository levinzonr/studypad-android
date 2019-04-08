package cz.levinzonr.studypad.presentation.screens.sharinghub.feed

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetRelevantNotebooks
import cz.levinzonr.studypad.domain.models.PublishedNotebook
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.SharedFragmentDirections
import timber.log.Timber

class SharingHubViewModel(private val getRelevantNotebooks: GetRelevantNotebooks) : BaseViewModel() {

    val dataSource = MutableLiveData<List<SectionResponse>>()

    init {
        toggleLoading(true)
        getRelevantNotebooks.execute {
            onComplete {
                toggleLoading(false)
                Timber.d("Sections: $it")
                dataSource.postValue(it)
            }
            onError {
                postError(it.message)
            }
        }
    }

    fun showDetail(notebook: PublishedNotebook.Feed) {
        navigateTo(
            SharedFragmentDirections.actionSharedFragmentToPublishedNotebookDetailFragment(
                notebook.id,
                notebook
            )
        )
    }

}