package dev.pseudo.testkinopoisk.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pseudo.testkinopoisk.domain.model.Film
import dev.pseudo.testkinopoisk.domain.repository.FilmRepository
import kotlinx.coroutines.launch

class FilmListViewModel(
    private val repository: FilmRepository
) : ViewModel() {

    private val _allFilms = MutableLiveData<List<Film>>()
    private val _filteredFilms = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> = _filteredFilms

    private val _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>> = _genres

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var selectedGenre: String? = null

    fun loadFilms() {
        _loading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val filmsList = repository.getFilms()
                    .sortedBy { it.localizedName.lowercase() }

                _allFilms.value = filmsList
                _filteredFilms.value = filmsList

                val genresSet = filmsList.flatMap { it.genres }.toSet().sorted()
                _genres.value = genresSet

                _loading.value = false
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.message}"
                _loading.value = false
            }
        }
    }

    fun onGenreSelected(genre: String?) {
        selectedGenre = genre
        val all = _allFilms.value ?: return

        _filteredFilms.value = if (genre == null) {
            all
        } else {
            all.filter { it.genres.contains(genre) }
        }
    }
}
