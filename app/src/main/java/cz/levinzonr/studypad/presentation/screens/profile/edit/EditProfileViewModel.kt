package cz.levinzonr.studypad.presentation.screens.profile.edit

import androidx.lifecycle.MutableLiveData
import cz.levinzonr.studypad.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.studypad.domain.interactors.keychain.UpdateUserInteractor
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.models.CurrentUserInfo
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.domain.models.UserProfile
import cz.levinzonr.studypad.presentation.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import timber.log.Timber

class EditProfileViewModel(
    userManager: UserManager,
    private val updateUserInteractor: UpdateUserInteractor
) : BaseViewModel() {

    val profileLiveData = MutableLiveData<CurrentUserInfo>()
    val stateLiveData = MutableLiveData<EditProfileViewState>()

    private var university: University? = null
    private var name: String = ""

    init {
        stateLiveData.postValue(EditProfileViewState())
        userManager.getCurrentUserInfo()?.let {
            university = it.university
            name = it.displayName
            profileLiveData.postValue(it)
        }
    }


    fun onUniversityChanged(university: University?) {
        this.university = university
        onUpdate()
    }

    fun onDisplayNameChanged(value: String) {
        this.name = value
        onUpdate()
    }

    fun onSaveProfileButtonClicked() {
        toggleLoading(true)
        updateUserInteractor.input = UpdateUserInteractor.Input(university, name)
        updateUserInteractor.execute {
            onComplete {
                toggleLoading(false)
                navigateBack()
            }
            onError {
                toggleLoading(false)
                handleApplicationError(it)
            }
        }
    }

    fun onSelectUniversityClicked() {
        navigateTo(EditProfileFragmentDirections.actionGlobalUniversitySelectorFragment(true))
    }

    private fun onUpdate() {
        val oldUni = profileLiveData.value?.university
        val oldName = profileLiveData.value?.displayName ?: ""
        val enableSave = oldName != name || university?.id != oldUni?.id
        Timber.d(" ${oldName != name} || ${university?.id != oldUni?.id} ")
        stateLiveData.postValue(stateLiveData.value?.copy(isSaveEnabled = enableSave))
    }

}