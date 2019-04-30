package cz.levinzonr.studypad.presentation.screens.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class OnboardingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }



    override val navHostFragment: Fragment by lazy { fragment }

    override val navController: NavController by lazy { findNavController(R.id.fragment)}
}
