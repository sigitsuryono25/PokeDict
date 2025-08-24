package com.surelabsid.core.utils.helpers

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.surelabsid.core.data.source.network.response.pokedetail.Other

fun Other.getAllImageUrls(): List<String?> {
    val urls = mutableListOf<String>()

    officialArtwork?.let {
        it.frontDefault?.let(urls::add)
        it.frontShiny?.let(urls::add)
    }
    home?.let {
        it.frontDefault?.let(urls::add)
        it.frontShiny?.let(urls::add)
        it.frontFemale?.let(urls::add)
        it.frontShinyFemale?.let(urls::add)
    }

    return urls
}

fun View.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.showView() {
    this.visibility = View.VISIBLE
}