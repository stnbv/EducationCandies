package com.example.educationcandies.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.educationcandies.R
import com.example.educationcandies.domain.FilmItem.Companion.UNDEFINED_ID
import com.example.educationcandies.presentation.ActivityMode.ADD_ITEM
import com.example.educationcandies.presentation.ActivityMode.UPDATE_ITEM
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FilmItemActivity : AppCompatActivity() {

    private lateinit var viewModel: FilmItemVM

    lateinit var nameTil: TextInputLayout
    lateinit var nameTiet: TextInputEditText
    lateinit var countTil: TextInputLayout
    lateinit var countTiet: TextInputEditText
    lateinit var saveBtn: Button

    private var screenMode = ""
    private var itemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[FilmItemVM::class.java]
        initViews()
        launchMode()
        observeViewModel()
        doOnTextChanged()
    }

    private fun launchMode() {
        when (screenMode) {
            ADD_ITEM.state -> launchAddMode()
            UPDATE_ITEM.state -> launchUpdateMode()
        }
    }

    private fun initViews() {
        nameTil = findViewById(R.id.nameTil)
        nameTiet = findViewById(R.id.nameTied)
        countTil = findViewById(R.id.countTil)
        countTiet = findViewById(R.id.countTied)
        saveBtn = findViewById(R.id.saveBtn)
    }

    private fun launchUpdateMode() {
        viewModel.getFilmItem(itemId)

        viewModel.filmItem.observe(this) {
            nameTiet.setText(it.name)
            countTiet.setText(it.count.toString())
        }

        saveBtn.setOnClickListener {
            viewModel.updateFilm(nameTiet.text.toString(), countTiet.text.toString())
        }
    }

    private fun launchAddMode() {
        saveBtn.setOnClickListener {
            viewModel.addFilm(nameTiet.text.toString(), countTiet.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.nameError.observe(this) {
            nameTil.error = if (it) "Error name" else null
        }

        viewModel.countError.observe(this) {
            countTil.error = if (it) "Error count" else null
        }

        viewModel.closeScreen.observe(this) {
            finish()
        }
    }

    private fun doOnTextChanged() {
        nameTiet.doOnTextChanged { p0: CharSequence?, p1: Int, p2: Int, p3: Int ->
            viewModel.resetErrorName()
        }
        countTiet.doOnTextChanged { p0: CharSequence?, p1: Int, p2: Int, p3: Int ->
            viewModel.resetErrorCount()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode absent")
        }
        val mode = intent.getStringExtra(SCREEN_MODE)
        if (mode != ActivityMode.UPDATE_ITEM.state && mode != ActivityMode.ADD_ITEM.state) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        if (mode == ActivityMode.UPDATE_ITEM.state) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)) {
                throw RuntimeException("Param $EXTRA_ITEM_ID not found")
            }
            itemId = intent.getIntExtra(EXTRA_ITEM_ID, UNDEFINED_ID)
        }
        screenMode = mode
    }
}