package cz.levinzonr.studypad.domain.managers

/**
 * A simple interface that is used to abstract Android StringsResource Handling
 */
interface TranslationManager {


    /**
     * Returns a translation string by the id specified
     * @param int is the id of the string resource
     * @return the translation
     */
    fun getResourceById(int: Int) : String
}