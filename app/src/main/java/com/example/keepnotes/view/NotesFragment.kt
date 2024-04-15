package com.example.keepnotes.view

import android.app.AlertDialog

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.keepnotes.MainActivity
import com.example.keepnotes.R
import com.example.keepnotes.adapter.NoteAdapter
import com.example.keepnotes.databinding.FragmentNotesBinding
import com.example.keepnotes.model.Note
import com.example.keepnotes.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.callbackFlow


class NotesFragment : Fragment(R.layout.fragment_notes) {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentNotesBinding>(
            inflater,
            R.layout.fragment_notes,
            container,
            false
        )
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        setUpNotesRecyclerView()
        binding.addNotebtn.setOnClickListener(View.OnClickListener {
            it.findNavController()
                .navigate(NotesFragmentDirections.actionNotesFragmentToAddNoteFragment())
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    activity.let {
                        noteViewModel.searchNote("%$newText").observe(viewLifecycleOwner) {
                            noteAdapter.notes = it
                        }
                    }
                }
                return false

            }
        })
    }

    fun updateUI(notes: List<Note>) {
        if (notes.isNullOrEmpty()) {
            binding.emptyNotesImage.visibility = VISIBLE
            binding.notesRecyclerView.visibility = GONE
            setHasOptionsMenu(false)
        } else {
            binding.notesRecyclerView.visibility = VISIBLE
            binding.emptyNotesImage.visibility = GONE
        }
    }

    fun setUpNotesRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.notesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = noteAdapter
            noteAdapter.notifyDataSetChanged()
        }

        activity.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
                noteAdapter.notes = notes
                updateUI(notes)
            }

        }
    }
}