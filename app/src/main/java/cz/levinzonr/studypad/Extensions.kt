package cz.levinzonr.studypad

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cz.levinzonr.studypad.presentation.base.BaseActivity
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.events.Event
import cz.levinzonr.studypad.presentation.events.SingleLiveEvent
import org.joda.time.*
import timber.log.Timber
import java.util.*


fun defaultLanguageCode(): String = Locale.getDefault().isO3Language

/**
 * Allows to iterate of the views in the ViewGroup
 */
fun ViewGroup.asSequence(): Sequence<View> = object : Sequence<View> {
    override fun iterator(): Iterator<View> = object : Iterator<View> {
        private var nextValue: View? = null
        private var done = false
        private var position: Int = 0

        override fun hasNext(): Boolean {
            if (nextValue == null && !done) {
                nextValue = getChildAt(position)
                position++
                if (nextValue == null) done = true
            }
            return nextValue != null
        }

        override fun next(): View {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            val answer = nextValue
            nextValue = null
            return answer!!
        }
    }
}

fun ViewGroup.removeIf(block: (View) -> Boolean) {
    asSequence().forEach {
        if (block(it)) removeView(it)
    }
}

fun ViewGroup.removeAllBut(id: Int) {
    asSequence().forEach {
        if (it.id != id) {
            Timber.d("Remove view")
            removeView(it)
        }
    }

}

fun SearchView.onQueryTextChanged(onChange: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            onChange(p0 ?: "")
            return true
        }
    })
}

fun androidx.appcompat.widget.SearchView.onQueryTextChanged(onChange: (String) -> Unit) {
    setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            onChange(p0 ?: "")
            return true
        }
    })
}

// Dp to int or vice versa
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)
val Float.px: Float
    get() = (this / Resources.getSystem().displayMetrics.density)


val ViewGroup.views: List<View>
    get() = asSequence().toList()

inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}

val BaseFragment.baseActivity: BaseActivity?
    get() = activity as? BaseActivity?

val BaseFragment.supportActionBar: ActionBar?
    get() = baseActivity?.supportActionBar


fun TextInputEditText.onTextChanged(onChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChange.invoke(p0.toString())
        }
    })
}

fun EditText.onTextChanged(onChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChange.invoke(p0.toString())
        }
    })
}

fun View.setVisible(visible: Boolean, fallback: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else fallback
}

fun MutableLiveData<SingleLiveEvent>.call() {
    postValue(SingleLiveEvent())
}

fun <T> MutableLiveData<Event<T>>.call(value: T) {
    postValue(Event(value))
}

fun MutableLiveData<SingleLiveEvent>.callIf(a: Boolean) {
    if (a) call()
}


fun MutableLiveData<SingleLiveEvent>.onHandle(lifecycleOwner: LifecycleOwner, block: () -> Unit) {
    observe(lifecycleOwner, Observer { it.handle(block) })
}

fun <T> MutableLiveData<Event<T>>.onHandle(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner, Observer { it.handle(block) })
}

fun String.isValidEmail(): Boolean {
    return this.matches(Patterns.EMAIL_ADDRESS.toRegex())
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}

fun String.isValidName(): Boolean {
    return this.matches(Regex("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}"))
}

fun liveEvent() = MutableLiveData<SingleLiveEvent>()

fun ImageView.loadAuthorImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .error(R.drawable.no_profile_image)
        .fallback(R.drawable.no_profile_image)
        .into(this)

}

fun <T> List<T>.first(n: Int): List<T> {
    return if (n > size) this else subList(0, n)
}

inline fun <T> List<T>.indexOfFirstOrNull(condition: (T) -> Boolean): Int? {
    return if (indexOfFirst { condition.invoke(it) } == -1) null else indexOfFirst { condition.invoke(it) }
}

fun Long.formatTime(context: Context): String? {
    val date = DateTime(this)
    val now = DateTime()

    val minutesBetween = Minutes.minutesBetween(date, now).minutes
    val hoursBetween = Hours.hoursBetween(date, now).hours
    val daysBetween = Days.daysBetween(date, now).days
    val monthsBetween = Months.monthsBetween(date, now).months

    Timber.d("Mins: $minutesBetween, hours: $hoursBetween, days: $daysBetween")
    return when {
        monthsBetween >= 1 -> context.getQuantityString(R.plurals.time_month, monthsBetween)
        daysBetween >= 1 -> context.getQuantityString(R.plurals.time_days, daysBetween)
        hoursBetween >= 1 -> context.getQuantityString(R.plurals.time_hours, hoursBetween)
        minutesBetween <= 0 -> context.getString(R.string.time_just_now)
        else -> context.getQuantityString(R.plurals.time_minutes, minutesBetween)

    }
}

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)


var TextView.shownText: String?
    get() = this.text.toString()
    set(value) {
        this.text = value
        this.setVisible(!value.isNullOrBlank())
    }

inline fun <reified T> Gson.fromJson(json: String): T? {
    val type = object : TypeToken<T>() {}.type
    return try {
        fromJson(json, type)
    } catch (e: Exception) {
        return null
    }
}

inline fun <T> LiveData<T>.observeNonNull(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    this.observe(lifecycleOwner, Observer {
        it?.let(observer)
    })
}


fun TextView.setSpannableText(text: String, vararg spans: String?) {
    val str = SpannableStringBuilder(text)
    Timber.d("Text: $text, Spans: ${spans.toList()}")
    spans.filterNotNull().forEach {
        val startIndex = text.indexOf(it)
        if (startIndex != -1) {
            val endIndex = startIndex + it.length
            str.setSpan(
                android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    setText(str)
}


fun Collection<String>.buildTags(context: Context): List<Chip> {
    return sortedBy { it.length }.map {
        Chip(context).apply {
            text = it
            isCheckable = false
            isClickable = false
        }
    }
}

fun String.toNotebookLink(): String {
    return "${BuildConfig.API_URL}/shared/$this"
}

fun Fragment.getQuantatyString(stirngRes: Int, quantaty: Int): String? {
    return context?.resources?.getQuantityString(stirngRes, quantaty, quantaty)
}

fun Context.getQuantityString(stringRes: Int, quantaty: Int): String? {
    return resources?.getQuantityString(stringRes, quantaty, quantaty)
}

fun View.showKeyboard() {
    requestFocus()
    val keyboard: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
}

fun View.hideKeyboard() {
    val keyboard: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboard.hideSoftInputFromWindow(this.windowToken, 0)
}