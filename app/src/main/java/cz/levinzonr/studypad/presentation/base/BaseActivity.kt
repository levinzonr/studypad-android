package cz.levinzonr.studypad.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.d("chage: ${destination.label}")
            Timber.d("TB: $supportActionBar")
            supportActionBar?.title = destination.label
        }
    }

    override fun onBackPressed() {
        val currentFragment = navHostFragment.childFragmentManager.fragments.first()
        if (currentFragment is BackButtonHandler) {
            currentFragment.handleBackButton()
        } else {
            super.onBackPressed()
        }
    }


    abstract val navHostFragment: Fragment

    abstract val navController: NavController
}