package dev.pseudo.testkinopoisk.presentation.ui

import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.pseudo.testkinopoisk.R
import dev.pseudo.testkinopoisk.databinding.FragmentFilmInfoBinding
import dev.pseudo.testkinopoisk.presentation.viewmodel.FilmInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmInfoFragment : Fragment() {

    private lateinit var binding: FragmentFilmInfoBinding
    private val args: FilmInfoFragmentArgs by navArgs()
    private val viewModel: FilmInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupToolbar()
        observeViewModel()

        viewModel.loadFilmById(args.filmId)
    }

    private fun setupToolbar() {
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.film.observe(viewLifecycleOwner) { film ->
            film?.let {
                binding.toolbarTitle.text = it.name
                binding.tvTitle.text = it.localizedName

                binding.tvGenresYear.text = if (it.genres.isNotEmpty()) {
                    "${it.genres.joinToString()} · ${it.year} год"
                } else {
                    "${it.year} год"
                }

                val rating = it.rating
                binding.tvRating.text = if (rating != null) {
                    val rounded = String.format("%.1f", rating)
                    val fullText = "$rounded КиноПоиск"

                    SpannableStringBuilder(fullText).apply {
                        setSpan(
                            AbsoluteSizeSpan(24, true),
                            0, rounded.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        setSpan(
                            StyleSpan(Typeface.BOLD),
                            0, rounded.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        setSpan(
                            AbsoluteSizeSpan(16, true),
                            rounded.length + 1, fullText.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                } else {
                    "Нет оценки"
                }

                binding.tvDescription.text = it.description ?: "Описание отсутствует"

                Glide.with(binding.ivPoster.context)
                    .load(film.imageUrl ?: R.drawable.bg_film_image)
                    .transform(CenterCrop(), RoundedCorners(dpToPx(4)))
                    .placeholder(R.drawable.bg_film_image)
                    .error(R.drawable.bg_film_image)
                    .into(binding.ivPoster)
            }
        }
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}
