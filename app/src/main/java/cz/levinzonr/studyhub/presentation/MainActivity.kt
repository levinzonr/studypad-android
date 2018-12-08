package cz.levinzonr.studyhub.presentation

import android.os.Bundle
import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
