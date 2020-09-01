package com.home.tribalapp.presentation.favoriteImages.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.home.tribalapp.R
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.domain.model.Favorite
import com.home.tribalapp.util.OnItemSelected
import com.home.tribalapp.util.loadUrl

class FavoriteViewHolder(
    private val view: View,
    private val onItemSelectedListener: OnItemSelected<Favorite>
) : RecyclerView.ViewHolder(view) {

    fun bind(item: Favorite) {
        val photoImageView = view.findViewById<ImageView>(R.id.item_favorite_photo_image_view)
        photoImageView.loadUrl(item.url)

        val username = view.findViewById<TextView>(R.id.item_username_text_view)
        username.text = item.user

        val favorite = view.findViewById<ImageView>(R.id.item_favorite_iv)
        favorite.setOnClickListener { onItemSelectedListener.onItemSelected(item) }

        val userPhotoImageView = view.findViewById<ImageView>(R.id.item_user_profile_fav_image_view)
        userPhotoImageView.loadUrl(item.userUrl)


    }

}