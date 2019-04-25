package cz.levinzonr.studypad.presentation.screens.notifications

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetNotificationsInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.MarkNotificationAsRead
import cz.levinzonr.studypad.domain.models.Notification
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotificationsViewModel(
    private val isPreviewMode: Boolean,
    private val getNotificationsInteractor: GetNotificationsInteractor,
    private val markNotificationAsRead: MarkNotificationAsRead) : BaseViewModel() {


    val notifications = MutableLiveData<List<Notification>>()
    init {
        refresh()
    }


    fun refresh() {
        toggleLoading(true)
        getNotificationsInteractor.execute {
            onComplete {
                val list = if (isPreviewMode) it.filter { !it.read }.first(2 ) else it
                toggleLoading(false)
                notifications.postValue(list)
            }
            onError { handleApplicationError(it) }
        }
    }

    fun onNotificationClicked(notification: Notification) {
        navigateTo(NotificationsFragmentDirections.actionGlobalPublishedNotebookDetailFragment(notification.notebookId))
    }


    fun markAsRead(list: List<Notification>) {
        val input = list.map { it.id }
        markNotificationAsRead.executeWithInput(input) {
            onComplete {
                refresh()
            }
            onError { handleApplicationError(it) }
        }
    }

}