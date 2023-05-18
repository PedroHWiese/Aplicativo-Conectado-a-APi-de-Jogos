package com.example.musicasmvvm.views

import android.annotation.SuppressLint
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicasmvvm.viewmodels.MusicasViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaPrincipal(
    musicasViewModel: MusicasViewModel = viewModel()
) {
    val navController = rememberNavController()

    val uiState by musicasViewModel.telaPrincipalUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = uiState.screenName)
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                musicasViewModel.navigate(navController)
            } ) {
                Icon(painter = painterResource(id = uiState.fabIcon), contentDescription = null)
            }
        }
    ) {
        NavHost(navController= navController, startDestination = "task_list"){
            composable("task_list"){
                TaskScreen(navController = navController, musicasViewModel = musicasViewModel)
            }
            composable("insert_edit_task"){
                InsertEditTaskScreen(navController = navController, musicasViewModel = musicasViewModel)
            }
        }
    }
}