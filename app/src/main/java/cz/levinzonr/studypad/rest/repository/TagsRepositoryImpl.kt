package cz.levinzonr.studypad.rest.repository

import com.google.gson.Gson
import cz.levinzonr.studypad.domain.models.StoredTag
import cz.levinzonr.studypad.domain.repository.TagsRepository
import cz.levinzonr.studypad.first
import cz.levinzonr.studypad.fromJson
import cz.levinzonr.studypad.domain.managers.PrefManager

class TagsRepositoryImpl(private val gson: Gson, private val prefManager: PrefManager) :
    TagsRepository {

    override suspend fun getAll(): List<StoredTag> {
        val string = prefManager.getString(PREF_TAGS, null) ?: return emptyList()
        return gson.fromJson(string) ?: emptyList()
    }

    override suspend fun getRecent(): List<String> {
        return getAll()
            .sortedByDescending { it.usedCount }
            .first(10)
            .map { it.value }
    }

    override suspend fun markAsChosen(list: List<String>) {
        val all = getAll()
        val newValue = list.map { tag ->
            val existed = all.firstOrNull { it.value == tag }
            existed?.copy(value = tag, usedCount = existed.usedCount + 1) ?: StoredTag(tag, 1)
        }
        prefManager.setString(PREF_TAGS, gson.toJson(newValue))
    }

    override suspend fun clear() {
        prefManager.remove(PREF_TAGS)
    }

    companion object {
        private const val PREF_TAGS = "pref_tags"
    }
}