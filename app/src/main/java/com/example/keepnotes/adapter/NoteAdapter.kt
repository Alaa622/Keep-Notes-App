package com.example.keepnotes.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.R
import com.example.keepnotes.databinding.ItemNoteBinding
import com.example.keepnotes.model.Note
import com.example.keepnotes.view.NotesFragmentDirections

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes = listOf<Note>()
    lateinit var noteViewHolder: NoteViewHolder

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.titleTv.text = note.title
            binding.noteTv.text = note.content

            binding.root.setOnClickListener(View.OnClickListener {
                it.findNavController().navigate(
                    NotesFragmentDirections.actionNotesFragmentToUpdateNoteFragment(
                        note
                    )
                )

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = DataBindingUtil.inflate<ItemNoteBinding>(
            LayoutInflater.from(parent.context), R.layout.item_note, parent, false
        )
        noteViewHolder= NoteViewHolder(binding)
        return noteViewHolder
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    fun getItem(): Note {
        return notes[noteViewHolder.adapterPosition]
    }

    fun setNotes(notes:List<Note>){
        this.notes=notes
    }

}