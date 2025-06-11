package dev.pseudo.testkinopoisk.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.pseudo.testkinopoisk.R
import dev.pseudo.testkinopoisk.databinding.ItemFilmBinding
import dev.pseudo.testkinopoisk.domain.model.Film

class FilmAdapter(
    private val onClick: (Film) -> Unit
) : ListAdapter<Film, FilmAdapter.FilmViewHolder>(DiffCallback) {

    inner class FilmViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.tvTitle.text = film.localizedName
            Glide.with(binding.ivPoster.context)
                .load(film.imageUrl ?: R.drawable.bg_film_image)
                .transform(CenterCrop(), RoundedCorners(dpToPx(4)))
                .placeholder(R.drawable.bg_film_image)
                .error(R.drawable.bg_film_image)
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                onClick(film)
            }
        }
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Film, newItem: Film) = oldItem == newItem
    }
}