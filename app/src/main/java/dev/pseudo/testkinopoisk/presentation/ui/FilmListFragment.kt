package dev.pseudo.testkinopoisk.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.pseudo.testkinopoisk.databinding.FragmentFilmListBinding
import dev.pseudo.testkinopoisk.presentation.adapter.FilmAdapter
import dev.pseudo.testkinopoisk.presentation.adapter.GenreAdapter
import dev.pseudo.testkinopoisk.presentation.viewmodel.FilmListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmListFragment : Fragment() {

    private lateinit var binding: FragmentFilmListBinding

    private val viewModel: FilmListViewModel by viewModel()
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var filmAdapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        genreAdapter = GenreAdapter { genre ->
            viewModel.onGenreSelected(genre)
        }

        filmAdapter = FilmAdapter { film ->
            findNavController().navigate(
                FilmListFragmentDirections.actionFilmListFragmentToFilmInfoFragment(film.id)
            )
        }

        binding.rvGenres.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.rvFilm.apply {
            adapter = filmAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        observeViewModel()
        viewModel.loadFilms()
    }

    private fun observeViewModel() {
        viewModel.films.observe(viewLifecycleOwner) {
            filmAdapter.submitList(it)
        }

        viewModel.genres.observe(viewLifecycleOwner) {
            genreAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbLoading.isVisible = isLoading
            binding.llContent.isVisible = !isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            val hasError = !errorMsg.isNullOrEmpty()

            binding.tvGenres.isVisible = !hasError
            binding.rvGenres.isVisible = !hasError
            binding.tvMovies.isVisible = !hasError
            binding.rvFilm.isVisible = !hasError

            if (hasError) {
                Snackbar.make(binding.root, errorMsg!!, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Повторить") {
                        viewModel.loadFilms()
                    }
                    .show()
            }
        }
    }
}