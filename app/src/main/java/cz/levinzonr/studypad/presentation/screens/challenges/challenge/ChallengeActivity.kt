package cz.levinzonr.studypad.presentation.screens.challenges.challenge

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.onHandle
import cz.levinzonr.studypad.presentation.base.BaseActivity
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeFragment
import cz.levinzonr.studypad.presentation.screens.challenges.learning.LearningChallengeFragment
import kotlinx.android.synthetic.main.activity_challenge.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChallengeActivity : AppCompatActivity() {

    private val args: ChallengeActivityArgs by navArgs()


    private val viewModel: ChallengeViewModel by viewModel { parametersOf(args.setup) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        viewModel.questionsLiveData.observeNonNull(this) {
            showChallenge(args.setup.currentType, it)
        }

        viewModel.repeatEvent.onHandle(this) {
            showChallenge(args.setup.currentType, viewModel.questionsLiveData.value ?: listOf())
        }

        viewModel.completedEvent.onHandle(this) {
            showFragment(ChallengeCompleteFragment.newInstance(it))
        }

        closeBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, TAG)
            .commit()
    }

    private fun showChallenge(type: ChallengeType, list: List<ChallengesModels.NoteItem>) {
        val fragment = when (type) {
            ChallengeType.Selfcheck -> LearningChallengeFragment.newInstance(list)
            ChallengeType.Write -> LearningChallengeFragment.newInstance(list)
            else -> FlashcardChallengeFragment.newInstance(list)
        }
        showFragment(fragment)
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG)
        if (fragment is ChallengeCompleteFragment) super.onBackPressed()
        else {
            AlertDialog.Builder(this)
                .setTitle("Leaving challenge")
                .setMessage("Are youre sure you want to leave this challenge? Progress won't be saved")
                .setPositiveButton(android.R.string.yes) { _, _ -> super.onBackPressed()}
                .setNegativeButton(android.R.string.no) { d, _ -> d.dismiss()}
                .show()
        }
    }

    companion object {
        private const val TAG = "tagsa"
    }

}
