package com.example.keepnotes.view

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
import androidx.navigation.fragment.findNavController
import com.example.keepnotes.MainActivity
import com.example.keepnotes.R
import com.example.keepnotes.databinding.FragmentAddNoteBinding
import com.example.keepnotes.model.Note
import com.example.keepnotes.viewmodel.NoteViewModel

class AddNoteFragment : Fragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_note, container, false
        )
        noteViewModel = (activity as MainActivity).noteViewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val title = binding.addNoteTitleEt.text.toString()
        val content = binding.addNoteContentEt.text.toString()

        if (item.itemId.equals(R.id.menu_save_btn)) {
            if (content == "") {
                Toast.makeText(context, "Please,Enter Your Note", Toast.LENGTH_SHORT).show()
                return false
            } else {
                noteViewModel.addNote(
                    Note(0, title, content)
                )
                Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()
                )
                return true
            }
        } else return false
    }

}