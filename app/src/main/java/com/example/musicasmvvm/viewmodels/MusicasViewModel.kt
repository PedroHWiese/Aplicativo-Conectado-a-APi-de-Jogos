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


    fun onMusicaIsFavoriteChange(updatedMusica: Musica, newFavoriteMusica: Boolean){
        val allSongsTemp = _musicasScreenUiState.value.allSongs.toMutableList()
        var pos  = 0
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

    fun onMusicaIsDeleted(deletedMusica: Musica){
        val allSongsTemp = _musicasScreenUiState.value.allSongs.toMutableList()
        var pos  = 0
        allSongsTemp.forEachIndexed{ index, musica ->
            if(deletedMusica == musica){
                pos = index
            }
        }
        allSongsTemp.removeAt(pos)
        _musicasScreenUiState.update { currentState ->
            currentState.copy(allSongs = allSongsTemp.toList())
        }
    }

    fun getFavoriteMusicas() {
        val allSongsTemp = _musicasScreenUiState.value.allSongs.toMutableList()
        val notfavoriteList = mutableListOf<Musica>()
        allSongsTemp.forEachIndexed { index, musica ->
            if (!musica.isFavorite) {
                notfavoriteList.add(musica)
            }
        }
        notfavoriteList.forEachIndexed{ index, musica ->
            notfavoriteList[index].isVisible = false
        }
        _musicasScreenUiState.update { currentState ->
            currentState.copy(isFiltered = true, allSongs = allSongsTemp)
        }
    }

    fun resetMusicas() {
        val allSongsTemp = _musicasScreenUiState.value.allSongs.toMutableList()
        allSongsTemp.forEachIndexed { index, musica ->
            musica.isVisible = true
        }
        _musicasScreenUiState.update { currentState ->
            currentState.copy(isFiltered = false, allSongs = allSongsTemp)
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
            }else {
                _telaPrincipalUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Adicionar Musica",
                        fabIcon = R.drawable.baseline_save_24
                    )
                }

                _editarInserirScreenUiState.update {
                    EditarInserirScreenUiState()
                }
            }
            navController.navigate("insert_edit_song")
        }else{
            _telaPrincipalUiState.update { currentState ->
                currentState.copy(
                    screenName = "Playlist",
                    fabIcon = R.drawable.baseline_playlist_add_24
                )
            }

            if(editMusica){
                val allSongsTemp = _musicasScreenUiState.value.allSongs.toMutableList()
                print(allSongsTemp)
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
            navController.navigate("song_list"){
                popUpTo("song_list"){
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
        if (_telaPrincipalUiState.value.screenName == "Atualizar Musica") {
            editMusica = false // Set editMusica to false
            musicaToEdit = Musica("")
        }

        _telaPrincipalUiState.update { currentState ->
            currentState.copy(
                screenName = "Playlist",
                fabIcon = R.drawable.baseline_playlist_add_24
            )
        }
        navController.popBackStack()
    }
}