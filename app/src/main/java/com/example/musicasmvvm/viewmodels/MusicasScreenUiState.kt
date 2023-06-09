package com.example.musicasmvvm.viewmodels

import com.example.musicasmvvm.models.Musica

data class MusicasScreenUiState(
    val allSongs: List<Musica> = listOf(
        Musica("Opeth - To Bid You Farewell",  "10:55", isFavorite = false ),
        Musica("Porcupine Tree - Anesthetize", "17:43", isFavorite = true),
    ),
    val isFiltered: Boolean = false,
    val isVisible: Boolean = false,
)
