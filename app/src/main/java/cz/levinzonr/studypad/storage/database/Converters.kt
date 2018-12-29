package cz.levinzonr.studypad.storage.database

import androidx.room.TypeConverter
import cz.levinzonr.studypad.domain.models.Color


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
}