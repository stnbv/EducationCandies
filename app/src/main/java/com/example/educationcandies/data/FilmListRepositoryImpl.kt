package com.example.educationcandies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.domain.repository.FilmListRepository
import kotlin.random.Random

object FilmListRepositoryImpl : FilmListRepository {

    private val candiesListLD = MutableLiveData<List<FilmItem>>()
    private val candiesList = sortedSetOf<FilmItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 19) {
            addFilm(
                FilmItem(
                    "https://kinoreporter.ru/wp-content/uploads/2021/10/breakfast-at-tiffanys-1-1024x686.jpg",
                    "Завтрак у Тиффани",
                    3,
                    Random.nextBoolean(),
                    i
                )
            )
        }
    }

    override fun addFilm(item: FilmItem) {
        if (item.id == FilmItem.UNDEFINED_ID) {
            item.id = autoIncrementId++  //сначал присвоится значение item.id, а позже autoIncrementId увеличится
        }
        candiesList.add(item)
        updateList()
    }

    override fun getFilmList(): LiveData<List<FilmItem>> {
        return candiesListLD
    }

    override fun getFilm(id: Int): FilmItem {
        return candiesList.find { it.id == id } ?: throw  RuntimeException("ELEMENT WITH ID = $id NOT FOUND")
    }

    override fun removeFilm(id: Int) {
        candiesList.removeIf { it.id == id }
        updateList()
    }

    override fun updateFilm(item: FilmItem) {
        removeFilm(item.id)
        addFilm(item)
    }

    private fun updateList() {
        candiesListLD.value = candiesList.toList()
    }
}