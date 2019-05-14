package cz.levinzonr.studypad.rest

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import cz.levinzonr.studypad.domain.repository.TokenRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.standalone.KoinComponent
import timber.log.Timber

/**
 * Authenticator class that is used to automatically refresh the token when the server responds with 401
 * @param repo is the tokenRepository which is used to set new token value
 */
class FirebaseAuthenticator(val repo: TokenRepository)  : Authenticator, KoinComponent {


    /**
     * Method where the authentication takes place
     * @param route of the request
     * @param response received from the API
     * @return modified request
     */
    override fun authenticate(route: Route?, response: Response): Request? {

        // We are interested in 401
        if (response.code() == 401) {

            val user = FirebaseAuth.getInstance().currentUser
            // We have the user logged in
            if (user != null) {

                // Request the new toke from the firebase sync
                val token = Tasks.await(user.getIdToken(true)) ?: return null

                // Save this token once refreshed
                repo.saveToken(token.token ?: "", token.expirationTimestamp )
                Timber.d("Token refreshed")

                // Return the old request with refreshed token
                return response.request().newBuilder()
                    .header("Firebase", token.token ?: "")
                    .build()
            } else {
                return null
            }
        } else {
            return null
        }
    }
}