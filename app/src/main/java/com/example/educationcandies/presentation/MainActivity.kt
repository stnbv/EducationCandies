package com.example.educationcandies.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.educationcandies.R
import com.example.educationcandies.R.layout
import com.example.educationcandies.domain.FilmItem
import com.example.educationcandies.presentation.ActivityMode.ADD_ITEM
import com.example.educationcandies.presentation.ActivityMode.UPDATE_ITEM
import com.example.educationcandies.presentation.adapter.DISABLED_VIEW
import com.example.educationcandies.presentation.adapter.ENABLED_VIEW
import com.example.educationcandies.presentation.adapter.FilmsAdapter
import com.example.educationcandies.presentation.adapter.MAX_POOL_SIZE
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val SCREEN_MODE = "screen_mode"
const val EXTRA_ITEM_ID = "extra_item_id"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var filmsAdapter: FilmsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupFilmsRecycler()

        viewModel.filmList.observe(this) {
            filmsAdapter.submitList(it)
        }

        findViewById<FloatingActionButton>(R.id.addItemBtn).setOnClickListener {
            navToAddOrUpdateItemActivity(ADD_ITEM)
        }
    }

    private fun setupFilmsRecycler() {
        filmsAdapter = FilmsAdapter { item -> clickAction(item) } //способ передачи метода через констуктор
        with(findViewById<RecyclerView>(R.id.filmsRv)) {
            adapter = filmsAdapter
            recycledViewPool.setMaxRecycledViews(ENABLED_VIEW, MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(DISABLED_VIEW, MAX_POOL_SIZE)
        }
        onLongClickAction()
        removeItemBySwipe()
    }

    private fun onLongClickAction() { //способ передачи метода в адаптер при помощи интерфейса
        filmsAdapter.onFilmItemLongClickListener = object : FilmsAdapter.OnFilmItemLongClickListener {
            override fun onFilmItemLongClick(filmItem: FilmItem) {
                viewModel.updateEnableState(filmItem)
            }
        }
    }

    private fun clickAction(item: FilmItem) {
        navToAddOrUpdateItemActivity(UPDATE_ITEM, item)
    }

    private fun removeItemBySwipe() {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val item = filmsAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeFilm(item.id)
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(findViewById(R.id.filmsRv))
    }

    private fun navToAddOrUpdateItemActivity(mode: ActivityMode, item: FilmItem? = null) {
        val intent = Intent(this, FilmItemActivity::class.java)
        when (mode) {
            UPDATE_ITEM -> {
                item?.let {
                    intent.putExtra(SCREEN_MODE, mode.state).putExtra(EXTRA_ITEM_ID, item.id)
                }
            }
            ADD_ITEM -> intent.putExtra(SCREEN_MODE, mode.state)
        }

        startActivity(intent)
    }
}

enum class ActivityMode(val state: String) {
    ADD_ITEM("mode_add"),
    UPDATE_ITEM("mode_update")
}