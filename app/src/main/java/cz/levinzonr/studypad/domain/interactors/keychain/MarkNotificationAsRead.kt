package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.rest.Api

class MarkNotificationAsRead(private val api: Api) : BaseInputInteractor<List<Long>, Unit>() {

    override suspend fun executeOnBackground(input: List<Long>) {
        api.markNotificationsAsReadAsync(input).await()
    }
}