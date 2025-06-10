package dev.pseudo.testkinopoisk.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.pseudo.testkinopoisk.databinding.FragmentFilmListBinding

class FilmListFragment : Fragment() {

    private lateinit var binding: FragmentFilmListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmListBinding.inflate(inflater, container, false)
        return binding.root
    }
}