package cz.levinzonr.studypad.presentation.screens.about


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import cz.levinzonr.studypad.BuildConfig

import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.notifications.IntentActions
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val versionInfo = getString(R.string.about_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        appVersionTv.text = versionInfo
        aboutLicenceBtn.setOnClickListener {
            startActivity( Intent(context, OssLicensesMenuActivity::class.java))
        }

        aboutPolicyBtn.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://ezpad-7e83e.firebaseapp.com/privacy_policy.html")
            })
        }

        aboutFeedbackBtn.setOnClickListener {
            findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToSendFeedbackFragment())
        }

    }


}