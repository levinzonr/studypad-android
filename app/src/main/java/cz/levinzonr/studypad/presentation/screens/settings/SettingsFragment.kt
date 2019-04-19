package cz.levinzonr.studypad.presentation.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.library.publish.LanguageSelectorDialog
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : BaseFragment() {

    override val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.settingsViewState.observeNonNull(viewLifecycleOwner) {
            settingsLanguagePrefTv.text = it.language?.displayName
            notificationsSwitch.isChecked = it.isNotificationsEnabled
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        settingsLogoutBtn.setOnClickListener {
            viewModel.onLogoutButtonClicked()
        }

        settingsDeleteAccountBtn.setOnClickListener {
            showToast("Not supported yet")
        }

        notificationsSwitch.setOnCheckedChangeListener { _, value ->
            viewModel.onChangeNotificationPreference(value)
        }

        settingsLanguageBtn.setOnClickListener {
            LanguageSelectorDialog.show(childFragmentManager) {
                viewModel.onChangeLocalePreference(it)
            }
        }
    }

}
