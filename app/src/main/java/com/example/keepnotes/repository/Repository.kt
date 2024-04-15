package com.example.keepnotes.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.keepnotes.database.NoteRoomDatabase
import com.example.keepnotes.model.Note

class Repository(private val database: NoteRoomDatabase) {

    suspend fun addNote(note: Note){
        database.noteDao().addNote(note)
    }

    suspend fun updateNote(note: Note){
        database.noteDao().updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        database.noteDao().deleteNote(note)
    }
    fun getAllNotes(): LiveData<List<Note>>{
        return database.noteDao().getAllNotes()
    }
    fun searchNote(query:String):LiveData<List<Note>>{
        return database.noteDao().searchNote(query)
    }
}