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
import androidx.recyclerview.widget.RecyclerView
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
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
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

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.pbLoading.isVisible = it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            binding.tvError.apply {
                isVisible = it != null
                text = it
            }
        }
    }
}