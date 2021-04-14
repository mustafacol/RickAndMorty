package com.mustafa.rickandmorty.models

data class CharacterResponse(
    val info: Info,
    val results: MutableList<Character>
)