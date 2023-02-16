package com.example.educationcandies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.educationcandies.data.FilmListRepositoryImpl
import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.domain.usecase.AddFilmUseCase
import com.example.educationcandies.domain.usecase.GetFilmUseCase
import com.example.educationcandies.domain.usecase.UpdateFilmUseCase

private const val DEFAULT_IMAGE =
    "https://www.megacritic.ru/media/reviews/photos/thumbnail/640x640s/9e/00/d5/982_poster_1253603681.jpg"

class FilmItemVM : ViewModel() {

    private val repository = FilmListRepositoryImpl

    val getFilmUseCase = GetFilmUseCase(repository)
    val addFilmUseCase = AddFilmUseCase(repository)
    val updateFilmUseCase = UpdateFilmUseCase(repository)

    //необходимо для того чтобы переменную нельзя было изменить из фрагмента
    private val _nameError = MutableLiveData<Boolean>()
    val nameError: LiveData<Boolean>
        get() = _nameError

    private val _countError = MutableLiveData<Boolean>()
    val countError: LiveData<Boolean>
        get() = _countError

    private val _filmItem = MutableLiveData<FilmItem>()
    val filmItem: LiveData<FilmItem>
        get() = _filmItem

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    fun getFilmItem(id: Int) {
        _filmItem.value = getFilmUseCase.getFilm(id)
    }

    fun addFilm(inputName: String?, inputCount: String?) {
        val count = parseCount(inputCount)
        val name = parseName(inputName)
        if (validateInput(name, count)) {
            addFilmUseCase.addFilm(FilmItem(DEFAULT_IMAGE, name, count, true))
            finishWork()
        }
    }

    fun updateFilm(inputName: String?, inputCount: String?) {
        val count = parseCount(inputCount)
        val name = parseName(inputName)
        if (validateInput(name, count)) {
            val filmItem = _filmItem.value
            filmItem?.let {
                val item = it.copy(count = count, name = name)
                updateFilmUseCase.updateFilm(item)
                finishWork()
            }

        }
    }

    private fun parseName(name: String?) = name?.trim() ?: ""

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
            _nameError.value = true
        }
        if (count <= 0) {
            result = false
            _countError.value = true
        }
        return result
    }

    fun resetErrorName() {
        _nameError.value = false
    }

    fun resetErrorCount() {
        _countError.value = false
    }

    private fun finishWork() {
        _closeScreen.value = Unit
    }
}