package com.example.musicasmvvm.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicasmvvm.network.GamesApi
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel(){

    var gamesUiState: String by mutableStateOf("colocar a resposta aqui")
        private set

    init {
        getAllGames()
    }


    fun getAllGames(){
        viewModelScope.launch {
        val listResult = GamesApi.retrofitService.getGames()
        gamesUiState = listResult
        }
    }



}