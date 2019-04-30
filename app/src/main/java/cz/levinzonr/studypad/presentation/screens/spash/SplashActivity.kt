package cz.levinzonr.studypad.presentation.screens.spash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.widget.Toast
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.presentation.common.StudyPadDialog
import cz.levinzonr.studypad.presentation.screens.MainActivity
import cz.levinzonr.studypad.presentation.screens.onboarding.OnboardingActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val userManager: UserManager by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resolve()
    }

    private fun resolve() {
        val intentData = intent.data
        if (intentData != null) {
            if (userManager.isLoggedIn()) {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    data = intentData
                })
                finish()
            } else {
                StudyPadDialog.Builder(this)
                    .setTitle(getString(R.string.not_logged_in_title))
                    .setMessage(getString(R.string.not_logged_in))
                    .setPositiveButton(getString(android.R.string.ok)) {
                        startActivity(Intent(this, OnboardingActivity::class.java))
                        finish()
                    }
                    .setOnCancelListener {
                        startActivity(Intent(this, OnboardingActivity::class.java))
                        finish() }
                    .show()

            }
        } else {
            if (userManager.isLoggedIn()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }
        }
    }

}
