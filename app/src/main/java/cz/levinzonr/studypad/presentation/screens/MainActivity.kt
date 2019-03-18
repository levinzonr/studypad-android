package cz.levinzonr.studypad.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setupKeyboardListener()
        with(findNavController(R.id.fragment)) {
            bottomNav.setupWithNavController(this)
            addOnDestinationChangedListener { _, destination, _ -> onDestinationChanged(destination.id) }
        }
    }

    private fun onDestinationChanged(destinationId: Int) {
        when (destinationId) {
            R.id.publishNotebookFragment -> hideMenu()
         //   R.id.noteDetailFragment -> hideMenu()
         //   R.id.editNoteFragment -> hideMenu()
            R.id.publishedNotebookDetailFragment -> hideMenu()
            else -> showMenu()
        }
    }


    private fun showMenu() {
        bottomNav.animate()
            .setStartDelay(0)
            .alpha(1.0f)
            .setDuration(5)
            .withEndAction { bottomNav.visibility = View.VISIBLE }
            .start()
    }


    private fun hideMenu() {
        bottomNav.visibility = View.GONE
        bottomNav.animate()
            .setStartDelay(0)
            .alpha(0.0f)
            .setDuration(5).start()
    }


    override val navHostFragment: Fragment by lazy { fragment }
}
