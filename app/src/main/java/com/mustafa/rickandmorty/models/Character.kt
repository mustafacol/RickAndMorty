package com.mustafa.rickandmorty.models

import java.io.Serializable

data class Character(
    val created: String,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val episode: Array<String>,
    val species: String,
    val status: String,
    val type: String
) : Serializable