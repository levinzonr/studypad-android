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
import cz.levinzonr.studypad.presentation.common.StudyPadDialog
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengesModels
import cz.levinzonr.studypad.presentation.screens.challenges.flashcards.FlashcardChallengeFragment
import cz.levinzonr.studypad.presentation.screens.challenges.learning.LearningChallengeFragment
import cz.levinzonr.studypad.presentation.screens.challenges.write.WriteChallengeFragment
import kotlinx.android.synthetic.main.activity_challenge.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.ext.checkedStringValue

class ChallengeActivity : AppCompatActivity() {

    private val args: ChallengeActivityArgs by navArgs()


    private val viewModel: ChallengeViewModel<*> by viewModel { parametersOf(args.setup) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)



        viewModel.questionsLiveData.observeNonNull(this) {
            showChallenge(args.setup.currentType, it)
        }

        viewModel.questionsLiveData.observeNonNull(this) {
            showChallenge(args.setup.currentType, viewModel.questionsLiveData.value ?: listOf())
        }

        viewModel.viewState.observeNonNull(this) {
            it.completedEvent?.handle {
                if (args.setup.currentType != ChallengeType.Flashcards) showFragment(ChallengeCompleteFragment.newInstance(it))
            }
            it.repeatChallenge?.handle {
                showChallenge(args.setup.currentType, viewModel.questionsLiveData.value ?: listOf())
            }
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
            ChallengeType.Write -> WriteChallengeFragment.newInstance(list)
            else -> FlashcardChallengeFragment.newInstance(list)
        }
        showFragment(fragment)
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG)
        when {
            fragment is ChallengeCompleteFragment -> super.onBackPressed()
            viewModel.challengeCompleted -> super.onBackPressed()
            else -> StudyPadDialog.Builder(this)
                .setTitle(getString(R.string.challenges_leave_title))
                .setMessage(getString(R.string.challenges_leave_message))
                .setPositiveButton(getString(android.R.string.yes)) {  super.onBackPressed()}
                .setNegativeButton(getString(android.R.string.no)) { d -> d.dismiss()}
                .show()
        }
    }

    companion object {
        private const val TAG = "tagsa"
    }

}
