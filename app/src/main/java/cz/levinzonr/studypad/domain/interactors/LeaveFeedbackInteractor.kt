package cz.levinzonr.studypad.domain.interactors

import android.os.Build
import cz.levinzonr.studypad.BuildConfig
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserFeedback

class LeaveFeedbackInteractor(private val userManager: UserManager) : BaseInputInteractor<String, Unit>() {

    override suspend fun executeOnBackground(input: String) {
        val buildVersion = BuildConfig.VERSION_NAME
        val buildNumber = BuildConfig.VERSION_CODE
        val device = "${Build.MANUFACTURER} ${Build.DEVICE}"
        val androidVersion = "${Build.VERSION.CODENAME} (${Build.VERSION.SDK_INT})"
        val userId = userManager.getCurrentUserInfo()?.id ?: ""
        val feedback = UserFeedback(buildVersion, buildNumber, androidVersion, device, userId, input)
    }
}