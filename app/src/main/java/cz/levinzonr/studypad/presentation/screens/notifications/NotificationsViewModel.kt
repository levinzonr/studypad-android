package cz.levinzonr.studypad.presentation.screens.notifications

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetNotificationsInteractor
import cz.levinzonr.studypad.domain.models.Notification
import cz.levinzonr.studypad.presentation.base.BaseViewModel

class NotificationsViewModel(private val getNotificationsInteractor: GetNotificationsInteractor) : BaseViewModel() {


    val notifications = MutableLiveData<List<Notification>>()
    init {
        refresh()
    }


    fun refresh() {
        getNotificationsInteractor.execute {
            onComplete {
                notifications.postValue(it.sortedByDescending { it.time })
            }
        }
    }

}