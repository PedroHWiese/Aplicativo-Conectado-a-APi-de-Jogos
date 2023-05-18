package com.example.musicasmvvm.viewmodels

data class EditarInserirScreenUiState(
    val nomeMusica: String = "",
    val duracaoMusica: String = "",
    val isFavorite: Boolean = false,
)
