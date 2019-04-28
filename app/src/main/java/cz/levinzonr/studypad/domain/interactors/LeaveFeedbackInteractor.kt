package cz.levinzonr.studypad.domain.interactors

import android.os.Build
import cz.levinzonr.studypad.BuildConfig
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.UserFeedback
import cz.levinzonr.studypad.rest.Api

class LeaveFeedbackInteractor(private val api: Api) : BaseInputInteractor<String, Unit>() {

    override suspend fun executeOnBackground(input: String) {
        val buildVersion = BuildConfig.VERSION_NAME
        val buildNumber = BuildConfig.VERSION_CODE
        val device = "${Build.MANUFACTURER} ${Build.MODEL} (${Build.DEVICE})"
        val androidVersion = "${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})"
        val feedback = UserFeedback(buildVersion, buildNumber, androidVersion, device, input)
        api.leaveFeedbackAsync(feedback).await()
    }
}