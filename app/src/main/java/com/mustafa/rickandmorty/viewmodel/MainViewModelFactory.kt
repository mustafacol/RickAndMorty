package com.mustafa.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafa.rickandmorty.repository.CharacterRepository

class MainViewModelFactory(val characterRepository: CharacterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(characterRepository) as T
    }
}