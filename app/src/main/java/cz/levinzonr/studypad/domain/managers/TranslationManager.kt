package cz.levinzonr.studypad.domain.managers

interface TranslationManager {

    fun getResourceById(int: Int) : String
}