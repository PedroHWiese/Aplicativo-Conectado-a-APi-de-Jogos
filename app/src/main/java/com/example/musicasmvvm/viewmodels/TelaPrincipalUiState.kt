package com.example.musicasmvvm.viewmodels

import androidx.annotation.DrawableRes
import com.example.musicasmvvm.R

data class TelaPrincipalUiState (
    val screenName: String = "Playlist",
    @DrawableRes val fabIcon: Int = R.drawable.baseline_playlist_add_24
)
