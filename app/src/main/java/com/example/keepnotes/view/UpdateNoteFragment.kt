package com.example.keepnotes.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.keepnotes.MainActivity
import com.example.keepnotes.R
import com.example.keepnotes.databinding.FragmentUpdateNoteBinding
import com.example.keepnotes.model.Note
import com.example.keepnotes.viewmodel.NoteViewModel


class UpdateNoteFragment : Fragment() {
    private lateinit var binding: FragmentUpdateNoteBinding
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update_note, container, false
        )
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!
        binding.editNoteTitleEt.setText(currentNote.title)
        binding.editNoteContentEt.setText(currentNote.content)
        binding.editNotebtn.setOnClickListener(View.OnClickListener {
            val updatedNote = Note(
                currentNote.id,
                binding.editNoteTitleEt.text.toString(),
                binding.editNoteContentEt.text.toString()
            )
            if (!(updatedNote.title.equals(currentNote.title)) ||
                !(updatedNote.content.equals(currentNote.content)))
            {
                noteViewModel.updateNote(updatedNote)
                Toast.makeText(context, "Note Edited", Toast.LENGTH_SHORT).show()
            }

            it.findNavController().navigate(
                UpdateNoteFragmentDirections.actionUpdateNoteFragmentToNotesFragment())

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId.equals(R.id.menu_delete_btn)) {
            AlertDialog.Builder(context).apply {
                setTitle("Delete Note")
                setMessage("Do you want to delete this note ?")
                setPositiveButton("Delete") { _, _ ->
                    noteViewModel.deleteNote(currentNote)
                    findNavController().navigate(UpdateNoteFragmentDirections.actionUpdateNoteFragmentToNotesFragment())
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel", null)
            }.create().show()

        }
        return true
    }
}