package com.example.musicasmvvm.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.musicasmvvm.viewmodels.MusicasViewModel

@Composable
fun InsertEditSongScreen(
    navController: NavController,
    musicasViewModel: MusicasViewModel
) {
    val uiState by musicasViewModel.editarInserirScreenUiState.collectAsState()
    BackHandler {
        musicasViewModel.onBackPressed(navController)
    }
    InsertEditForm(
        nome = uiState.nomeMusica,
        duracao = uiState.duracaoMusica,
        onNomeChange = {musicasViewModel.onMusicaNomeChange(it)},
        onDuracaoChange = {musicasViewModel.onMusicaDuracaoChange(it)},
    )
}

@Composable
fun InsertEditForm(
    nome: String,
    duracao: String,
    onNomeChange: (String) -> Unit,
    onDuracaoChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            OutlinedTextField(
                label= {Text(text = "Nome da música")},
                value = nome, onValueChange = onNomeChange
            )
            OutlinedTextField(
                label= {Text(text = "Duração da música")},
                value = duracao, onValueChange = onDuracaoChange
            )
        }
    }
}