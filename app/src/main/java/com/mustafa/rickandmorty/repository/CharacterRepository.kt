package com.mustafa.rickandmorty.repository

import com.mustafa.rickandmorty.api.RetrofitInstance

class CharacterRepository {
    suspend fun getCharacters(page: Int) = RetrofitInstance.api.getCharacters(page)

}