package com.home.tribalapp.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

// this file holds all the method extensions of the project

/**
 * Opens the keyboard using the view itself.
 */
fun View.openKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Closes the keyboard using the view itself.
 */
fun View.closeKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * View extensions
 */
fun ImageView.loadUrl(urlToImage: String?) {
    Picasso.get()
        .load(urlToImage)
        .into(this)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Context.showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

