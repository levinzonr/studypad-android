package cz.levinzonr.studypad.presentation.screens.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class OnboardingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }


    override val navHostFragment: Fragment by lazy { fragment }
}
