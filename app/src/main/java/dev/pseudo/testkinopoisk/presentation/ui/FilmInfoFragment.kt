package dev.pseudo.testkinopoisk.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.pseudo.testkinopoisk.databinding.FragmentFilmInfoBinding

class FilmInfoFragment : Fragment() {

    private lateinit var binding: FragmentFilmInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}