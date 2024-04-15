package com.example.keepnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.keepnotes.database.NoteRoomDatabase
import com.example.keepnotes.model.Note
import com.example.keepnotes.repository.Repository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository:Repository
    init {
        repository=Repository(NoteRoomDatabase.getDatabase(application))
    }
     fun addNote(note: Note){
         viewModelScope.launch {
             repository.addNote(note)
         }
    }
    fun updateNote(note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)

        }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.getAllNotes()
    }

    fun searchNote(query:String):LiveData<List<Note>>{
        return repository.searchNote(query)
    }
}