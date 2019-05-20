package cz.levinzonr.studypad.domain.managers

/**
 * An abstraction layer interface for android shared preferences
 */
interface PrefManager {

    fun getInt(key : String, defaultValue : Int) : Int
    fun setInt(key : String, value : Int)
    fun getLong(key : String, defaultValue : Long) : Long
    fun setLong(key : String, value : Long)
    fun getBoolean(key : String, defaultValue : Boolean) : Boolean
    fun setBoolean(key : String, value : Boolean)
    fun getFloat(key : String, defaultValue : Float) : Float
    fun setFloat(key : String, value : Float)
    fun getString(key : String, defaultValue : String?) : String?
    fun setString(key : String, value : String)
    fun remove(key : String)
}