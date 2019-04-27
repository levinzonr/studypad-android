package cz.levinzonr.studypad.presentation.screens.challenges.challenge


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.navArgs
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeStats
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType
import kotlinx.android.synthetic.main.fragment_challenge_complete.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.roundToInt

class ChallengeCompleteFragment : BaseFragment() {

    private val args: ChallengeCompleteFragmentArgs by navArgs()

    override val viewModel: ChallengeViewModel<*> by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_complete, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<ChallengeStats>(ARG_STATS)?.let(this::showChallengeResults)
    }


    private fun showChallengeResults(challengeStats: ChallengeStats) {
        val textType = challengeStats.type.getName(context!!)
        val percentage = (challengeStats.correctCount * 100) / challengeStats.total
        showProgress(percentage)
        challengeCompleteProgressTv.text = getString(R.string.challenge_completed_percentage, percentage)
        challengeCompleteTv.text = getString(R.string.challenge_completed_message, textType)
        challengeCompleteBtnRepeat.setOnClickListener {
            viewModel.repeatChallenge()
        }

        challengeCompleteExitBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun subscribe() {


    }


    private fun showProgress(percent: Int) {
        // turn
        challengeCompletePBB.progress = 0
        challengeCompletePBB.alpha = 1.0f
        challengeCompletePBB.visibility = View.VISIBLE
        val a = ProgressAnimation(challengeCompletePBB)
        a.interpolator = FastOutSlowInInterpolator()
        a.duration = 1800
        a.toProgress(Math.min(percent, challengeCompletePBB.max))

        challengeCompleteProgressTv.text = "$percent%"

        completenessFadeIn()
    }

    private fun completenessFadeIn() {
        challengeCompleteProgressTv.alpha = 0.0f
        challengeCompleteProgressTv.visibility = View.VISIBLE
        challengeCompleteProgressTv.animate().alpha(1.0f).setDuration(1600L).start()
    }

    private fun completenessFadeOut() {
        challengeCompleteProgressTv.animate().alpha(0.0f).setDuration(2000L).start()
        challengeCompletePBB.animate().alpha(0.0f).setDuration(2000L).start()

    }

    companion object {
        private const val ARG_STATS = "Sdasdas"
        fun newInstance(stats: ChallengeStats) : ChallengeCompleteFragment {
            return ChallengeCompleteFragment().apply {
                arguments = Bundle().apply { putParcelable(ARG_STATS, stats) }
            }
        }
    }

}

class ProgressAnimation(private val progressBar: ProgressBar) : Animation() {
    private var from: Int = 0
    private var to: Int = 0

    fun toProgress(progressPercent: Int) {
        from = progressBar.progress
        to = (progressPercent*progressBar.max)/100
        progressBar.startAnimation(this)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val progress = (1 - interpolatedTime) * from + interpolatedTime * to
        progressBar.progress = progress.roundToInt()
        progressBar.invalidate()
    }
}