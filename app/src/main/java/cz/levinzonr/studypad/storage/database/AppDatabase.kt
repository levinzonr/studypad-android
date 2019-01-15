package cz.levinzonr.studypad.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.levinzonr.studypad.domain.models.Note
import cz.levinzonr.studypad.domain.models.Notebook
import cz.levinzonr.studypad.storage.database.daos.NoteDao
import cz.levinzonr.studypad.storage.database.daos.NotebookDao

@Database(entities = [Notebook::class, Note::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun notebookDao() : NotebookDao

    abstract fun notesDao() : NoteDao


}