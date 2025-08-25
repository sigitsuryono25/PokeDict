package com.surelabsid.core.utils.helpers

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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

fun Activity.getTypeColor(type: String?): Int {
    return when (type.toString().lowercase()) {
        "normal" -> "#A8A77A".toColorInt()
        "fire" -> "#EE8130".toColorInt()
        "water" -> "#6390F0".toColorInt()
        "electric" -> "#F7D02C".toColorInt()
        "grass" -> "#7AC74C".toColorInt()
        "ice" -> "#96D9D6".toColorInt()
        "fighting" -> "#C22E28".toColorInt()
        "poison" -> "#A33EA1".toColorInt()
        "ground" -> "#E2BF65".toColorInt()
        "flying" -> "#A98FF3".toColorInt()
        "psychic" -> "#F95587".toColorInt()
        "bug" -> "#A6B91A".toColorInt()
        "rock" -> "#B6A136".toColorInt()
        "ghost" -> "#735797".toColorInt()
        "dragon" -> "#6F35FC".toColorInt()
        "dark" -> "#705746".toColorInt()
        "steel" -> "#B7B7CE".toColorInt()
        "fairy" -> "#D685AD".toColorInt()
        else -> ContextCompat.getColor(this, android.R.color.darker_gray)
    }
}

fun Fragment.checkIfFragmentAttached(operation: FragmentActivity.() -> Unit) {
    if (isAdded && activity != null && !parentFragmentManager.isStateSaved) {
        operation(requireActivity())
    }
}


fun View.showKeyboard() {
    this.requestFocus()
    post {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
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