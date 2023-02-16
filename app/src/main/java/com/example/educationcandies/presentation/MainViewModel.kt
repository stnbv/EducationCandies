package com.example.educationcandies.presentation

import androidx.lifecycle.ViewModel
import com.example.educationcandies.data.FilmListRepositoryImpl
import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.domain.usecase.GetFilmListUseCase
import com.example.educationcandies.domain.usecase.RemoveFilmUseCase
import com.example.educationcandies.domain.usecase.UpdateFilmUseCase

class MainViewModel : ViewModel() {

    private val repository = FilmListRepositoryImpl

    private val getCandiesListUseCase = GetFilmListUseCase(repository)
    private val removeCandyUseCase = RemoveFilmUseCase(repository)
    private val updateCandyUseCase = UpdateFilmUseCase(repository)

    var filmList = getCandiesListUseCase.getFilmList()

    fun removeFilm(id: Int) {
        removeCandyUseCase.removeFilm(id)
    }

    fun updateFilm(item: FilmItem) {
        updateCandyUseCase.updateFilm(item)
    }

    fun updateEnableState(item: FilmItem) {
        updateCandyUseCase.updateFilm(item.copy(enabled = !item.enabled))
    }
}
