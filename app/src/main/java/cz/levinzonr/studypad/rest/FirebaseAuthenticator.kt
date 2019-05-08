package cz.levinzonr.studypad.rest

import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import cz.levinzonr.studypad.storage.TokenRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.standalone.KoinComponent
import timber.log.Timber

class FirebaseAuthenticator(val repo: TokenRepository)  : Authenticator, KoinComponent {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == 401) {
            Timber.d("401")

            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val token = Tasks.await(user.getIdToken(true)) ?: return null
                Timber.d("token: $token")
                repo.saveToken(token.token ?: "", token.expirationTimestamp )
                Timber.d("Token refreshed")
                return response.request().newBuilder()
                    .header("Firebase", token.token ?: "")
                    .build()
            } else {
                Timber.d("User isnull")
                return null
            }
        } else {
            return null
        }
    }
}