package cz.levinzonr.studypad.domain.managers

import android.content.Context

class TranslationManagerImpl(private val context: Context) : TranslationManager {
    override fun getResourceById(int: Int): String {
        return context.getString(int)
    }
}