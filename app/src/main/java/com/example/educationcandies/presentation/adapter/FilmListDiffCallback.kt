package com.example.educationcandies.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.educationcandies.domain.FilmItem

class FilmListDiffCallback : DiffUtil.ItemCallback<FilmItem>() {

    //сравнивает объекты, чтобы адаптер понял сравнивает он одни и те же объекты или разные
    override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
        return oldItem == newItem
    }
}
