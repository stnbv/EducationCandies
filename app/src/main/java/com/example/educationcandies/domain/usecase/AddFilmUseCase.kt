package com.example.educationcandies.domain.usecase

import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.domain.repository.FilmListRepository

class AddFilmUseCase(private val filmListRepository: FilmListRepository) {

    fun addFilm(item: FilmItem) {
        filmListRepository.addFilm(item)
    }
}
