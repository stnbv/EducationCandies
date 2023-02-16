package com.example.educationcandies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.educationcandies.R
import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.presentation.adapter.FilmsAdapter.FilmViewHolder

const val ENABLED_VIEW = 0
const val DISABLED_VIEW = 1
const val MAX_POOL_SIZE = 10

class FilmsAdapter(
    private val makeToastOnClick: (item: FilmItem) -> Unit
) : ListAdapter<FilmItem, FilmViewHolder>(FilmListDiffCallback()) {


    var onFilmItemLongClickListener: OnFilmItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = when (viewType) {
            ENABLED_VIEW -> R.layout.item_film
            DISABLED_VIEW -> R.layout.item_open_film
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val item = getItem(position)
        Glide
            .with(holder.filmInageView)
            .load(item)
            .centerCrop()
            .into(holder.filmInageView);
        holder.filmName.text = item.name
        holder.view.setOnLongClickListener {
            onFilmItemLongClickListener?.onFilmItemLongClick(item)
            true
        }
        holder.view.setOnClickListener {
            makeToastOnClick.invoke(item)
        }

        holder.view
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) ENABLED_VIEW else DISABLED_VIEW
    }

    class FilmViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val filmInageView = view.findViewById<ImageView>(R.id.filmIv)
        val filmName = view.findViewById<TextView>(R.id.filmTv)
    }

    interface OnFilmItemLongClickListener {
        fun onFilmItemLongClick(filmItem: FilmItem)
    }
}
