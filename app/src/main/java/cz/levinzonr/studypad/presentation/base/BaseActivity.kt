package cz.levinzonr.studypad.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController

abstract class BaseActivity : AppCompatActivity() {


    override fun onBackPressed() {
        val currentFragment = navHostFragment.childFragmentManager.fragments.first()
        if (currentFragment is BackButtonHandler) {
            currentFragment.handleBackButton()
        } else {
            super.onBackPressed()
        }
    }



    abstract val navHostFragment: Fragment
}