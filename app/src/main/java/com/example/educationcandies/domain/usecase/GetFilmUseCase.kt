package com.example.educationcandies.domain.usecase

import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.domain.repository.FilmListRepository

class GetFilmUseCase(private val filmListRepository: FilmListRepository) {

    fun getFilm(id: Int): FilmItem {
        return filmListRepository.getFilm(id)
    }
}
