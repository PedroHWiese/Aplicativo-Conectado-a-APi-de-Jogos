package com.example.musicasmvvm.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicasmvvm.R
import com.example.musicasmvvm.models.Musica
import com.example.musicasmvvm.viewmodels.MusicasViewModel

@Composable
fun SongScreen(
    navController: NavController,
    musicasViewModel: MusicasViewModel
) {

    val uiState by musicasViewModel.musicasScreenUiState.collectAsState()

    SongList(
        musicas = uiState.allSongs,
        onFavoriteChange = {musica, isFavorite ->
            musicasViewModel.onMusicaIsFavoriteChange(musica, isFavorite)
        },
        onEditMusica = { musicasViewModel.editMusica(it, navController)}
    )
}

@Composable
fun SongList(
    musicas: List<Musica>,
    onFavoriteChange: (Musica, Boolean) -> Unit,
    onEditMusica: (Musica) -> Unit,
) {
    LazyColumn(){
        items(musicas){ musica ->
            SongEntry(musicas = musica, onFavoriteChange = {onFavoriteChange(musica, it)}, onEditMusica = {onEditMusica(musica)})
        }
    }
}

@Composable
fun SongEntry(
    musicas: Musica,
    onFavoriteChange: (Boolean) -> Unit,
    onEditMusica: () -> Unit,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .clickable { onEditMusica() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row() {

                Text( modifier = Modifier.padding(start = 8.dp), text=musicas.name)
                Text( modifier = Modifier.padding(start = 8.dp), text = musicas.duracao)

            }
            IconButton(
                onClick = { onFavoriteChange(!musicas.isFavorite) }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (musicas.isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                    ),
                    contentDescription = if (musicas.isFavorite) "Checked" else "Unchecked"
                )
            }
        }
    }
}