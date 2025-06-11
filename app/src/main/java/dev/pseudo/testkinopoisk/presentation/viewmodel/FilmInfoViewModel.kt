package dev.pseudo.testkinopoisk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pseudo.testkinopoisk.domain.model.Film
import dev.pseudo.testkinopoisk.domain.repository.FilmRepository
import kotlinx.coroutines.launch

class FilmInfoViewModel(private val repository: FilmRepository) : ViewModel() {

    private val _film = MutableLiveData<Film?>()
    val film: LiveData<Film?> = _film

    fun loadFilmById(id: Int) {
        viewModelScope.launch {
            val films = repository.getFilms()
            _film.value = films.find { it.id == id }
        }
    }
}