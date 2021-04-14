package com.mustafa.rickandmorty.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.rickandmorty.models.CharacterResponse
import com.mustafa.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(val characterRepository: CharacterRepository) : ViewModel() {
    var characters: MutableLiveData<CharacterResponse> = MutableLiveData()
    var characterResponse: CharacterResponse? = null
    var page = 1

    init {
        getCharacters()
    }


    fun getCharacters() = viewModelScope.launch {
        val response = characterRepository.getCharacters(page)
        val handlerResponse = handleGetCharactersResponse(response)
        if (handlerResponse != null) {
            characters.postValue(handlerResponse)
        }
    }

    private fun handleGetCharactersResponse(response: Response<CharacterResponse>): CharacterResponse? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                page++
                if (characterResponse == null) {
                    characterResponse = result
                } else {
                    val newArticles = result?.results
                    characterResponse?.results?.addAll(newArticles)
                }

                return characterResponse ?: result
            }
        }
        return null
    }

}


