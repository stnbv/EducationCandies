package com.example.educationcandies.domain.usecase

import androidx.lifecycle.LiveData
import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.domain.repository.FilmListRepository

class GetFilmListUseCase(private val filmListRepository: FilmListRepository) {

    fun getFilmList(): LiveData<List<FilmItem>> {
        return filmListRepository.getFilmList()
    }
}
