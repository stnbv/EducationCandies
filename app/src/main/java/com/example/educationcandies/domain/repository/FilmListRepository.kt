package com.example.educationcandies.domain.repository

import androidx.lifecycle.LiveData
import com.example.educationcandies.domain.FilmItem

interface FilmListRepository {

    fun addFilm(item: FilmItem)

    fun getFilmList(): LiveData<List<FilmItem>>

    fun getFilm(id: Int): FilmItem

    fun removeFilm(id: Int)

    fun updateFilm(item: FilmItem)
}
