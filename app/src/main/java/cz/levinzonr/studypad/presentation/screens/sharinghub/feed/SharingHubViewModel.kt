package cz.levinzonr.studypad.presentation.screens.sharinghub.feed

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.data.SectionResponse
import cz.levinzonr.studypad.domain.interactors.GetNotificationsInteractor
import cz.levinzonr.studypad.domain.interactors.sharinghub.GetRelevantNotebooks
import cz.levinzonr.studypad.domain.managers.SearchManager
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.sharinghub.search.NotebookSearchModels
import timber.log.Timber

class SharingHubViewModel(
    private val userManager: UserManager,
    private val getRelevantNotebooks: GetRelevantNotebooks) : BaseViewModel() {

    val dataSource = MutableLiveData<List<Section>>()

    init {
        toggleLoading(true)
        getRelevantNotebooks.execute {
            onComplete {
                toggleLoading(false)
                Timber.d("Sections: $it")
                val data = it.map { it.toDomain() }
                dataSource.postValue(data)
            }
            onError {
                handleApplicationError(it)
            }
        }

    }

    fun showDetail(notebook: PublishedNotebook.Feed) {
        navigateTo(
            SharingHubFragmentDirections.actionGlobalPublishedNotebookDetailFragment(
                notebook.id,
                notebook
            )
        )
    }




    fun onSearchButtonClicked() {
        navigateTo(SharingHubFragmentDirections.actionSharedFragmentToNotebooksSearchFragment())
    }

    fun onSearchEntryClicked(searchEntry: SearchEntry) {
        val state = NotebookSearchModels.SearchState(searchEntry.university, searchEntry.topics, searchEntry.tags, searchEntry.query)
        navigateTo(SharingHubFragmentDirections.actionSharedFragmentToNotebooksSearchFragment(state))
    }

    fun onShowAllNotificationsClicked() {
        navigateTo(SharingHubFragmentDirections.actionSharingHubFragmentToNotificationsFragment())
    }

    fun onShowAllClicked(section: Section) {
        val searchState = when (section.type) {
            SectionType.SCHOOL -> {
                val university = userManager.getCurrentUserInfo()?.university
                NotebookSearchModels.SearchState(university =  university)
            }
            SectionType.RECENT -> NotebookSearchModels.SearchState(orderBy = OrderBy.RECENT)
            SectionType.POPULAR -> NotebookSearchModels.SearchState(orderBy = OrderBy.POPULARITY)
            SectionType.UNKNOWN -> NotebookSearchModels.SearchState()
        }
        navigateTo(SharingHubFragmentDirections.actionSharedFragmentToNotebooksSearchFragment(searchState))
    }

}