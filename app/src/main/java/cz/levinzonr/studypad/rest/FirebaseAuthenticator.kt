package cz.levinzonr.studypad.rest

import com.google.firebase.auth.FirebaseAuth
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

class FirebaseAuthenticator  : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == 401) {
            Timber.d("401")
            val user = FirebaseAuth.getInstance().currentUser ?: return null
            val token = user.getIdToken(true).result?.token ?: return null
            Timber.d("Token refreshed")
            return response.request().newBuilder()
                .header("Firebase", token)
                .build()
        } else {
            return null
        }
    }
}