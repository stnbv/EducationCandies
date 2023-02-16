package com.example.educationcandies.domain.usecase

import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.domain.repository.FilmListRepository

class UpdateFilmUseCase(private val filmListRepository: FilmListRepository) {

    fun updateFilm(item: FilmItem) {
        filmListRepository.updateFilm(item)
    }
}
