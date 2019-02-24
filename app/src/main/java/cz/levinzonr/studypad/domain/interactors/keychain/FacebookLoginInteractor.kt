package cz.levinzonr.studypad.domain.interactors.keychain

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserProfile

class FacebookLoginInteractor(private val userManager: UserManager) : BaseInputInteractor<String, UserProfile>() {


    override suspend fun executeOnBackground(input: String) : UserProfile {
        return userManager.loginViaFacebook(input)
    }
}