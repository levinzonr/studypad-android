package cz.levinzonr.studypad.presentation.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.crashlytics.android.Crashlytics
import cz.levinzonr.studypad.NavigationMainDirections
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.notifications.IntentActions
import cz.levinzonr.studypad.notifications.NotificationPayload
import cz.levinzonr.studypad.presentation.base.BaseActivity
import cz.levinzonr.studypad.presentation.base.NotificationHandler
import cz.levinzonr.studypad.presentation.screens.notifications.NotificationType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import timber.log.Timber
import q.rorbin.badgeview.QBadgeView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.presentation.screens.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import q.rorbin.badgeview.Badge


class MainActivity : BaseActivity() {


    private lateinit var broadcastReceiver: BroadcastReceiver

    private var badgeView: Badge? = null

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerBroadcastReceiver()
      //  setupKeyboardListener()
        setSupportActionBar(toolbar)
        handleDeepLink()
        initBadgeView()
        profileViewModel.profileLiveData.observeNonNull(this) {
            badgeView?.badgeNumber = it.notificationsCount
        }

        with(findNavController(R.id.fragment)) {
            bottomNav.setupWithNavController(this)
            addOnDestinationChangedListener { _, destination, _ ->
                toolbar.setupWithNavController(this)
                onDestinationChanged(destination.id)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcastReceiver()
    }

    private fun handleDeepLink() {
        val notificationPayload = intent.getParcelableExtra<NotificationPayload>("payload")
        val deeploink = intent?.data

        val notebookId = when{
            notificationPayload != null -> notificationPayload.notebookInfo.notebookId
            deeploink?.lastPathSegment != null -> deeploink.lastPathSegment
            else -> null
        }
        notebookId?.let { id ->
            navController.navigate(NavigationMainDirections.actionGlobalPublishedNotebookDetailFragment(id))}
    }

    private fun registerBroadcastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val currentFragment = navHostFragment.childFragmentManager.fragments.first()
                if (currentFragment is NotificationHandler) {
                    val paylaod = p1?.getParcelableExtra<NotificationPayload>("data") ?: return
                    currentFragment.handleNotification(NotificationType.valueOf(paylaod.type.capitalize()), paylaod)
                } else {
                    badgeView?.badgeNumber = (badgeView?.badgeNumber ?: 0).inc()
                }
            }
        }
        val intentFilter = IntentFilter(IntentActions.NOTIFICATION)
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)

    }

    private fun initBadgeView() {
        val bottomNavigationMenuView = bottomNav.getChildAt(0) as BottomNavigationMenuView
        val v = bottomNavigationMenuView.getChildAt(1) // number of menu from left
        badgeView = QBadgeView(this).bindTarget(v)
        val blue = ContextCompat.getColor(this, R.color.blue)
        badgeView?.badgeNumber = 0
    }

    private fun unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
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
        Timber.d("Toolbar: ${supportActionBar?.isShowing}")

        if (destinationId == R.id.sharingHubFragment) badgeView?.hide(true)

        when (destinationId) {
            R.id.publishNotebookFragment ->  {
                hideMenu()
                supportActionBar?.hide()
            }
            R.id.noteDetailFragment, R.id.reviewSuggestionsFragment, R.id.learningChallengeFragment -> {
                hideMenu()
            }
            //   R.id.editNoteFragment -> hideMenu()
            R.id.publishedNotebookDetailFragment, R.id.challengeCompleteFragment, R.id.universitySelectorFragment-> {
                supportActionBar?.hide()
                hideMenu()
            }

            R.id.notebooksSearchFragment -> {
                supportActionBar?.hide()
                hideMenu()
            }


            else ->  {
                supportActionBar?.show()
                showMenu()
            }
        }

    }


    private fun showMenu() {
        bottomNav.visibility = View.VISIBLE
        /*bottomNav.animate()
            .setStartDelay(0)
            .alpha(1.0f)
            .setDuration(1000)
            .withEndAction { }
            .start()*/
    }


    private fun hideMenu() {
        bottomNav.visibility = View.GONE

      /*  bottomNav.animate()
            .setStartDelay(0)
            .alpha(0.0f)
            .setDuration(1000).withEndAction {

            }.start()*/
    }

    private fun setupKeyboardListener() {
        val activityRootView = findViewById<ViewGroup>(R.id.activityRoot)
        activityRootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            activityRootView.getWindowVisibleDisplayFrame(r)

            val heightDiff = activityRootView.rootView.height - (r.bottom - r.top)
            // This value is pretty random, and I'm not sure it's 100% correct
            // RE: Was failing on some devices, changed to another random value
            if (heightDiff > 252) {
                hideMenu()
            } else {
                showMenu()
            }
        }
    }


    override val navHostFragment: Fragment by lazy { fragment }

    override val navController: NavController by lazy { findNavController(R.id.fragment) }
}
