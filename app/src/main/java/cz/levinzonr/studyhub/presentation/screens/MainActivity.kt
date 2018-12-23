package cz.levinzonr.studyhub.presentation.screens

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cz.levinzonr.studyhub.R
import cz.levinzonr.studyhub.domain.repository.NotesRepository
import cz.levinzonr.studyhub.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : BaseActivity() {

    private val repository: NotesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appToolbar)
        with(findNavController(R.id.fragment)) {
            bottom_nav.setupWithNavController(this)
            setupActionBarWithNavController(this)
        }

        repository.getNotesFromNotebook(1).observe(this, Observer {
            Timber.d("Reposriroy test: $it" )
        })
    }
}
