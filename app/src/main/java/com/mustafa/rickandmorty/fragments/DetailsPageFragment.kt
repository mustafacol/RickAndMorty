package com.mustafa.rickandmorty.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mustafa.rickandmorty.MainActivity
import com.mustafa.rickandmorty.R
import com.mustafa.rickandmorty.models.Character
import com.mustafa.rickandmorty.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.character_cell.view.*
import kotlinx.android.synthetic.main.fragment_details_page.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class DetailsPageFragment : Fragment(R.layout.fragment_details_page) {

    private lateinit var character: Character
    private lateinit var mainViewModel: MainViewModel
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.getSerializable("character") != null) {
                character = it.getSerializable("character") as Character
                Log.d("Character", character.toString())
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
        setupUI()

    }

    private fun setupToolbar() {
        toolbar = (activity as MainActivity).findViewById(R.id.mytoolbar)
        toolbar.backButton.visibility = View.VISIBLE
        toolbar.headerTitle.text = character.name
        toolbar.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_detailsPageFragment_to_mainPageFragment)
        }
    }


    private fun setupUI() {
        setupToolbar()
        detailTvStatus.text = character.status
        detailProfileContainer.setBackgroundResource(
            when (character.status) {
                "Dead" -> R.drawable.death_gradient_background
                "Alive" -> R.drawable.alive_gradient_background
                else -> R.drawable.unknown_gradient_background
            }
        )
        detailTvLocation.text = character.location.name
        detailTvOrigin.text = character.origin.name
        detailTvSpecies.text = character.species
        detailTvName.text = character.name
        Glide.with(this).load(character.image).into(detailProfileImage)

        val formatter = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val date: LocalDateTime = LocalDateTime.parse(character.created, formatter)

        detailTvCreated.text =
            date.monthValue.toString() + "/" + date.dayOfMonth.toString() + "/" + date.year.toString()
    }
}