package com.example.educationcandies.domain.usecase

import com.example.educationcandies.domain.repository.FilmListRepository

class RemoveFilmUseCase(private val filmListRepository: FilmListRepository) {

    fun removeFilm(id: Int) {
        filmListRepository.removeFilm(id)
    }
}
