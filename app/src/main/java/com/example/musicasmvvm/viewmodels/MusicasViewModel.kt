package com.example.musicasmvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.musicasmvvm.R
import com.example.musicasmvvm.models.Musica
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MusicasViewModel: ViewModel() {
    private var _telaPrincipalUiState: MutableStateFlow<TelaPrincipalUiState> = MutableStateFlow(
        TelaPrincipalUiState()
    )
    val telaPrincipalUiState: StateFlow<TelaPrincipalUiState> = _telaPrincipalUiState.asStateFlow()

    private var _musicasScreenUiState: MutableStateFlow<MusicasScreenUiState> = MutableStateFlow(
        MusicasScreenUiState()
    )
    val musicasScreenUiState: StateFlow<MusicasScreenUiState> = _musicasScreenUiState.asStateFlow()

    private var _editarInserirScreenUiState: MutableStateFlow<EditarInserirScreenUiState> = MutableStateFlow(
        EditarInserirScreenUiState()
    )
    val editarInserirScreenUiState: StateFlow<EditarInserirScreenUiState> = _editarInserirScreenUiState.asStateFlow()

    var editMusica: Boolean = false
    var musicaToEdit: Musica = Musica("")

    fun onMusicaNomeChange(newMusicaNome: String){
        _editarInserirScreenUiState.update { currentState ->
            currentState.copy(nomeMusica = newMusicaNome)
        }
    }

    fun onMusicaDuracaoChange(newDuracao: String){
        _editarInserirScreenUiState.update { currentState ->
            currentState.copy(duracaoMusica = newDuracao)
        }
    }

    /*fun onMusicaIsFavoriteChange(newFavorite: Boolean){
        _editarInserirScreenUiState.update { currentState ->
            currentState.copy(isFavorite = newFavorite)
        }
    }*/

    fun onMusicaIsFavoriteChange(updatedMusica: Musica, newFavoriteMusica: Boolean){
        val allSongsTemp = _musicasScreenUiState.value.allSongs.toMutableList()
        var pos  = -1
        allSongsTemp.forEachIndexed{ index, musica ->
            if(updatedMusica == musica){
                pos = index
            }
        }
        allSongsTemp.removeAt(pos)
        allSongsTemp.add(pos, updatedMusica.copy(isFavorite = newFavoriteMusica))
        _musicasScreenUiState.update { currentState ->
            currentState.copy(allSongs = allSongsTemp.toList())
        }
    }

    fun navigate(navController: NavController){
        if(_telaPrincipalUiState.value.screenName == "Playlist"){
            if(editMusica){
                _telaPrincipalUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Atualizar Musica",
                        fabIcon = R.drawable.baseline_save_24
                    )
                }
            }else{
                _telaPrincipalUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Adicionar Musica",
                        fabIcon = R.drawable.baseline_save_24
                    )
                }
            }
            navController.navigate("insert_edit_task")
        }else{
            _telaPrincipalUiState.update { currentState ->
                currentState.copy(
                    screenName = "Playlist",
                    fabIcon = R.drawable.baseline_playlist_add_24
                )
            }

            if(editMusica){
                val allSongsTemp = _musicasScreenUiState.value.allSongs.toMutableList()
                var pos  = -1
                allSongsTemp.forEachIndexed{ index, musica ->
                    if(musicaToEdit == musica){
                        pos = index
                    }
                }
                allSongsTemp.removeAt(pos)
                allSongsTemp.add(pos, musicaToEdit.copy(
                    name = _editarInserirScreenUiState.value.nomeMusica,
                    duracao = _editarInserirScreenUiState.value.duracaoMusica,
                    isFavorite = _editarInserirScreenUiState.value.isFavorite
                ))
                _musicasScreenUiState.update { currentState ->
                    currentState.copy(allSongs = allSongsTemp.toList())
                }
            }else{
                _musicasScreenUiState.update { currentState ->
                    currentState.copy(
                        allSongs = currentState.allSongs + Musica(
                            name = _editarInserirScreenUiState.value.nomeMusica,
                            duracao = _editarInserirScreenUiState.value.duracaoMusica,
                            isFavorite = _editarInserirScreenUiState.value.isFavorite
                        )
                    )
                }
            }
            _editarInserirScreenUiState.update {
                EditarInserirScreenUiState()
            }
            editMusica = false
            musicaToEdit = Musica("")
            navController.navigate("task_list"){
                popUpTo("task_list"){
                    inclusive = true
                }
            }

        }
    }

    fun editMusica(musica: Musica, navController: NavController){
        editMusica= true
        musicaToEdit = musica
        _editarInserirScreenUiState.update { currentState ->
            currentState.copy(
                nomeMusica = musica.name,
                duracaoMusica = musica.duracao,
                isFavorite = musica.isFavorite,
            )
        }
        navigate(navController)
    }

    fun onBackPressed(navController: NavController){
        _telaPrincipalUiState.update { currentState ->
            currentState.copy(
                screenName = "Playlist",
                fabIcon = R.drawable.baseline_playlist_add_24
            )
        }
        navController.popBackStack()
    }
}