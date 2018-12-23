package cz.levinzonr.studyhub

import androidx.appcompat.app.ActionBar
import cz.levinzonr.studyhub.presentation.base.BaseActivity
import cz.levinzonr.studyhub.presentation.base.BaseFragment


val BaseFragment.baseActivity : BaseActivity?
    get() = activity as? BaseActivity?

val BaseFragment.supportActionBar: ActionBar?
    get() = baseActivity?.supportActionBar