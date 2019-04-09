package cz.levinzonr.studypad.storage.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import cz.levinzonr.studypad.domain.models.Color
import cz.levinzonr.studypad.domain.models.OrderBy
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University
import cz.levinzonr.studypad.fromJson


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
    fun fromIntToOrderyBy(numeral: Int?): OrderBy? {
        for (ds in OrderBy.values()) {
            if (ds.ordinal == numeral) {
                return ds
            }
        }
        return null
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

}