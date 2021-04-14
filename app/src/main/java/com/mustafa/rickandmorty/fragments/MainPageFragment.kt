package com.mustafa.rickandmorty.fragments

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.rickandmorty.MainActivity
import com.mustafa.rickandmorty.R
import com.mustafa.rickandmorty.adapter.CharactersAdapter
import com.mustafa.rickandmorty.repository.CharacterRepository
import com.mustafa.rickandmorty.viewmodel.MainViewModel
import com.mustafa.rickandmorty.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_main_page.*
import java.util.Observer


class MainPageFragment : Fragment(R.layout.fragment_main_page) {
    // TODO: Rename and change types of parameters
    private lateinit var mainViewModel: MainViewModel
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var toolbar: Toolbar
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel


        setupToolbar()
        setupRecyclerView()
        setClickListeners()
        observeCharacters()
    }


    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (checkPaginate()) {
                mainViewModel.getCharacters()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            isScrolling = true

        }
    }

    private fun setupToolbar(){
        toolbar = (activity as MainActivity).findViewById(R.id.mytoolbar)
        toolbar.backButton.visibility=View.GONE
        toolbar.headerTitle.text="Characters"
    }

    private fun setClickListeners(){
        charactersAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("character", it)
            }
            findNavController().navigate(
                R.id.action_mainPageFragment_to_detailsPageFragment,
                bundle
            )


        }
    }

    private fun observeCharacters() {
        showProgressBar()
        mainViewModel.characters.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { response ->

                if (response.results != null) {
                    charactersAdapter.differ.submitList(response.results)

                    charactersAdapter.notifyDataSetChanged()
                    isLastPage = mainViewModel.page == 34

                    hideProgressBar()
                }
            })
    }

    private fun setupRecyclerView() {
        charactersAdapter = CharactersAdapter()
        recyclerView.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(scrollListener)

        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun checkPaginate(): Boolean {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
        val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isNotAtBeginning = firstVisibleItemPosition >= 0
        val isTotalMoreThanVisible = totalItemCount >= 20
        val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                isTotalMoreThanVisible && isScrolling
        return shouldPaginate
    }

}