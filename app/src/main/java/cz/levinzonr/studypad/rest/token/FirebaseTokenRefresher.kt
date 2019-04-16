package cz.levinzonr.studypad.rest.token

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import dk.nodes.okhttputils.session.TokenRefreshmentResult
import dk.nodes.okhttputils.session.base.AccessTokenRefresher

class FirebaseTokenRefresher : AccessTokenRefresher {

    override fun refreshToken(): TokenRefreshmentResult {
        val user = FirebaseAuth.getInstance().currentUser ?: return TokenRefreshmentResult.Error(true)
        val result =  Tasks.await(user.getIdToken(true)).token
        return if (result != null)
            TokenRefreshmentResult.Success(result)
        else TokenRefreshmentResult.Error(false)
    }
}

