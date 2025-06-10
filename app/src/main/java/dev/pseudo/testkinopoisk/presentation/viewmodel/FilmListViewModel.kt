package dev.pseudo.testkinopoisk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pseudo.testkinopoisk.domain.model.Film
import dev.pseudo.testkinopoisk.domain.repository.FilmRepository
import kotlinx.coroutines.launch

class FilmListViewModel(private val repository: FilmRepository) : ViewModel() {
    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> = _films

    fun loadFilms() {
        viewModelScope.launch {
            try {
                _films.value = repository.getFilms()
            } catch (e: Exception) {

            }
        }
    }
}
