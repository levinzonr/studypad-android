package cz.levinzonr.studypad.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import timber.log.Timber

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setupKeyboardListener()
        with(findNavController(R.id.fragment)) {
            bottomNav.setupWithNavController(this)
            addOnDestinationChangedListener { _, destination, _ ->
                configureToolbar(destination)
                onDestinationChanged(destination.id) }
        }
    }

    private fun configureToolbar(navDestination: NavDestination) {

    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        val navDestination = navController.currentDestination ?: return
        supportActionBar?.title = navDestination.label
        if (navDestination.id != R.id.notebookListFragment) {
            Timber.d("Back Avil")
            toolbar?.let {
                it.setNavigationIcon(R.drawable.ic_chevron_right_black_24dp)
                it.setNavigationOnClickListener { navController.navigateUp() }
            }
        }
    }

    private fun onDestinationChanged(destinationId: Int) {
        when (destinationId) {
            R.id.publishNotebookFragment -> hideMenu()
            R.id.noteDetailFragment -> hideMenu()
         //   R.id.editNoteFragment -> hideMenu()
            R.id.publishedNotebookDetailFragment -> hideMenu()
            else -> showMenu()
        }
    }


    private fun showMenu() {
        bottomNav.animate()
            .setStartDelay(0)
            .alpha(1.0f)
            .setDuration(100)
            .withEndAction { bottomNav.visibility = View.VISIBLE }
            .start()
    }


    private fun hideMenu() {
        bottomNav.visibility = View.GONE
        bottomNav.animate()
            .setStartDelay(0)
            .alpha(0.0f)
            .setDuration(100).start()
    }


    override val navHostFragment: Fragment by lazy { fragment }

    override val navController: NavController by lazy { findNavController(R.id.fragment) }
}
