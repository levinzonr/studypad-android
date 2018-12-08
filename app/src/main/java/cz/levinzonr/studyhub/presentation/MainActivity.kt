package cz.levinzonr.studyhub.presentation

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_nav.setupWithNavController(findNavController(R.id.fragment))
    }
}
