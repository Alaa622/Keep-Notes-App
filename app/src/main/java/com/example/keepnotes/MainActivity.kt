package com.example.keepnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.keepnotes.viewmodel.NoteViewModel
import com.example.keepnotes.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         noteViewModel = ViewModelProvider(
            this,
            NoteViewModelFactory(application)
        )[NoteViewModel::class.java]

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.start_nav_host) as NavHostFragment
        navController=navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this,navController)

    }
    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp()
    }
}