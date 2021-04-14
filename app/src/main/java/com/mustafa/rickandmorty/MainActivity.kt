package com.mustafa.rickandmorty

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatViewInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.rickandmorty.adapter.CharactersAdapter
import com.mustafa.rickandmorty.repository.CharacterRepository
import com.mustafa.rickandmorty.viewmodel.MainViewModel
import com.mustafa.rickandmorty.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val characterRepository = CharacterRepository()
        val viewModelProviderFactory = MainViewModelFactory(characterRepository)
        mainViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)


    }


}