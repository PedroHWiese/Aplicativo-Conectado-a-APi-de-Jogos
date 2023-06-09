package com.example.musicasmvvm.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicasmvvm.R
import com.example.musicasmvvm.viewmodels.MusicasViewModel
import com.example.musicasmvvm.viewmodels.MarsViewModel



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaPrincipal(
    musicasViewModel: MusicasViewModel = viewModel(),
    marsViewModel: MarsViewModel = viewModel()
) {
    val navController = rememberNavController()

    val uiState by musicasViewModel.telaPrincipalUiState.collectAsState()
    val uiStateFilter by musicasViewModel.musicasScreenUiState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar( title = {

                Text( text = uiState.screenName, color = Color(0xFF3A3A3A))
            },  colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF84F17C)))
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                musicasViewModel.navigate(navController)
            },
                Modifier.padding(start = 310.dp)
            ) {
                Icon(
                    painter = painterResource(id = uiState.fabIcon),
                    contentDescription = null
                )
            }
            if (uiState.screenName == "Playlist" && uiStateFilter.isFiltered == false) {
                FloatingActionButton(onClick = {
                    musicasViewModel.getFavoriteMusicas()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_24),
                        contentDescription = null
                    )
                }
            }
            if (uiState.screenName == "Playlist" && uiStateFilter.isFiltered == true) {
                FloatingActionButton(onClick = {
                    musicasViewModel.resetMusicas()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                        contentDescription = null
                    )
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(top=70.dp)
        ) {


            NavHost(navController = navController, startDestination = "song_list") {
                composable("song_list") {
                    SongScreen(navController = navController, musicasViewModel = musicasViewModel)
                }
                composable("insert_edit_song") {
                    InsertEditSongScreen(navController = navController, musicasViewModel = musicasViewModel)
                }

            }
        }
    }

}