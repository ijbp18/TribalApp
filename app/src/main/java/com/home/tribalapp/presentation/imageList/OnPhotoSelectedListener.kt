package com.home.tribalapp.presentation.imageList

import com.home.tribalapp.data.model.Image

interface OnPhotoSelectedListener {

    /**
     * When a specified item is selected.
     *
     * @param item image item selected
     */
    fun onPhotoSelected(item: Image)

    /**
     * When a specified item is selected.
     *
     * @param item image item selected
     */
    fun onPhotoFavoriteSelected(item: Image)

}
