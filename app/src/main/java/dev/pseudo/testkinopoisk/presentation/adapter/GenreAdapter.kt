package dev.pseudo.testkinopoisk.presentation.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.pseudo.testkinopoisk.R
import dev.pseudo.testkinopoisk.databinding.ItemGenreBinding

class GenreAdapter(
    private val onClick: (String?) -> Unit
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var genres: List<String> = emptyList()
    private var selectedGenre: String? = null

    fun submitList(newList: List<String>) {
        genres = newList
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: String) {
            val isSelected = genre == selectedGenre

            binding.tvGenre.apply {
                text = genre
                setTypeface(null, if (isSelected) Typeface.BOLD else Typeface.NORMAL)
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (isSelected) R.color.purple else android.R.color.black
                    )
                )
                setBackgroundResource(
                    if (isSelected) R.drawable.bg_selected_genre else R.drawable.bg_unselected_genre
                )

                setOnClickListener {
                    selectedGenre = if (isSelected) null else genre
                    onClick(selectedGenre)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount() = genres.size
}