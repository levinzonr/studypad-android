package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.StoredTag

interface TagsRepository {

    suspend fun getAll() : List<StoredTag>

    suspend fun getRecent() : List<String>

    suspend fun markAsChosen(list: List<String>)

    suspend fun clear()

}