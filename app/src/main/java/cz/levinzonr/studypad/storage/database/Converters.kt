package cz.levinzonr.studypad.storage.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import cz.levinzonr.studypad.domain.models.*
import cz.levinzonr.studypad.fromJson
import cz.levinzonr.studypad.presentation.screens.challenges.ChallengeType


class Converters {

    @TypeConverter
    fun fromStringToColor(color: String) : Color {
        val colors = color.split("_")
        return Color(colors.first(), colors[1])
    }

    @TypeConverter
    fun fromColorToString(color: Color) : String {
        return "${color.startColor}_${color.endColor}"
    }

    @TypeConverter
    fun fromUniversityToString(university: University?) : String {
        return Gson().toJson(university)
    }

    @TypeConverter
    fun fromStringToUniversity(string: String) : University? {
        return Gson().fromJson(string)
    }


    @TypeConverter
    fun fromTopicsToString(topics: List<Topic>) : String {
        return Gson().toJson(topics)
    }

    @TypeConverter
    fun fromStringToTopics(string: String) : List<Topic> {
        return Gson().fromJson(string) ?: listOf()
    }


    @TypeConverter
    fun fromListToString(list: List<String>) : String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String) : List<String> {
        return Gson().fromJson(string) ?: listOf()
    }

    @TypeConverter
    fun fromOrderByToInt(status: OrderBy): Int? {
        return status.ordinal
    }

    @TypeConverter
    fun fromIntToOrderyBy(numeral: Int?): OrderBy? {
        for (ds in OrderBy.values()) {
            if (ds.ordinal == numeral) {
                return ds
            }
        }
        return null
    }

    @TypeConverter
    fun fromTypeByToInt(status: ChallengeType): Int? {
        return status.ordinal
    }

    @TypeConverter
    fun fromIntToType(numeral: Int?): ChallengeType? {
        for (ds in ChallengeType.values()) {
            if (ds.ordinal == numeral) {
                return ds
            }
        }
        return null
    }


    @TypeConverter
    fun fromStringToNotebook(string: String) : Notebook? {
        return Gson().fromJson(string)
    }

    @TypeConverter
    fun fromNotebookToString(notebook: Notebook): String {
        return Gson().toJson(notebook)
    }
}