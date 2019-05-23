package cz.levinzonr.studypad.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.levinzonr.studypad.domain.models.ChallengeEntry
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.domain.models.SearchEntry
import cz.levinzonr.studypad.storage.database.daos.ChallengeDao
import cz.levinzonr.studypad.storage.database.daos.NoteDao
import cz.levinzonr.studypad.storage.database.daos.NotebookDao
import cz.levinzonr.studypad.storage.database.daos.SearchEntryDao

/**
 * An abstract class for Room to create the database instance
 */
@Database(entities = [Notebook::class, Note::class, SearchEntry::class, ChallengeEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun notebookDao() : NotebookDao

    abstract fun notesDao() : NoteDao

    abstract fun searchEntryDao() : SearchEntryDao

    abstract fun challengesDao() : ChallengeDao

}