package cz.levinzonr.studypad.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appToolbar)
        with(findNavController(R.id.fragment)) {
            bottom_nav.setupWithNavController(this)
            setupActionBarWithNavController(this)
        }
    }

    override val navHostFragment: Fragment by lazy { fragment }
}
