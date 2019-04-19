package cz.levinzonr.studypad.presentation.screens.settings

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.keychain.LogoutInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.Locale
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import cz.levinzonr.studypad.presentation.screens.Flow

class SettingsViewModel(
    private val logoutInteractor: LogoutInteractor,

    private val userManager: UserManager) : BaseViewModel() {

    val settingsViewState: MutableLiveData<SettingsViewState> = MutableLiveData()
    init {
        userManager.getCurrentUserInfo()?.let {
            val prefs = userManager.getPreferences()
            settingsViewState.postValue(SettingsViewState(prefs.isNotificationsEnabled, it.chosenLocale))
        }
    }

    fun onChangeNotificationPreference(newValue: Boolean) {
        userManager.setNotificationsEnabled(newValue)
    }

    fun onChangeLocalePreference(locale: Locale) {
        userManager.setLocale(locale)
        settingsViewState.postValue(settingsViewState.value?.copy(language  = locale))
    }

    fun onLogoutButtonClicked() {
        logoutInteractor.execute {
            onComplete {
                changeFlow(Flow.ONBOARDING)
            }
        }
    }
}