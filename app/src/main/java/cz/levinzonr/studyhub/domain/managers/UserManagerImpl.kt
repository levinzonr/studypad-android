package cz.levinzonr.studyhub.domain.managers

import cz.levinzonr.studyhub.data.EmailLoginRequest
import cz.levinzonr.studyhub.rest.utils.Api

class UserManagerImpl(val api: Api) : UserManager {



    override suspend fun login(email: String, password: String) {
        val request = EmailLoginRequest(email, password)
        api.loginUsingEmail(request).await()
    }
}