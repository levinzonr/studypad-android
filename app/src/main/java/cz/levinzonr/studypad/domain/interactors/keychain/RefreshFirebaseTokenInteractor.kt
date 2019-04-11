package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInteractor
import cz.levinzonr.studypad.domain.repository.FirebaseTokenRepository
import cz.levinzonr.studypad.rest.Api

class RefreshFirebaseTokenInteractor(private val api: Api,
                                     private val tokenRepository: FirebaseTokenRepository) : BaseInteractor<Boolean>() {


    override suspend fun executeOnBackground(): Boolean {
        tokenRepository.getToken()?.let {
            api.registerFirebaseTokenAsync(it).await()
            tokenRepository.markAsRefreshed()
            return true
        }
        return false
    }
}