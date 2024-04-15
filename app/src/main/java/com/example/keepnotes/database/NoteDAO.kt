package com.example.keepnotes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.keepnotes.model.Note

@Dao
interface NoteDAO {
    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("select * from Note order by id desc")
    fun getAllNotes():LiveData<List<Note>>

    @Query("select * from Note where content Like :query or title like :query order by id desc")
    fun searchNote(query:String):LiveData<List<Note>>
}