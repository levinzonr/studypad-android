package cz.levinzonr.studypad.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import timber.log.Timber

/**
 * Base Activity for the other activities to extend.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.d("chage: ${destination.label}")
            Timber.d("TB: $supportActionBar")
            supportActionBar?.title = destination.label
        }
    }


    /**
     * Allows to propagate back button action onto currently displayed fragment
     */
    override fun onBackPressed() {

        // Check which fragment is currently active/visible
        val currentFragment = navHostFragment.childFragmentManager.fragments.first()

        // If it is a buttonHanlder, let the fragment do the work
        if (currentFragment is BackButtonHandler) {
            currentFragment.handleBackButton()
        } else {
            // Just navigate back otherwise
            super.onBackPressed()
        }
    }

    fun navigateBack() {
        super.onBackPressed()
    }


    abstract val navHostFragment: Fragment

    abstract val navController: NavController
}