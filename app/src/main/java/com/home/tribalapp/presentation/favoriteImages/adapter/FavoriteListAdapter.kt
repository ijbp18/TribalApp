package com.home.tribalapp.presentation.favoriteImages.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.home.tribalapp.R
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.domain.model.Favorite
import com.home.tribalapp.presentation.favoriteImages.holder.FavoriteViewHolder
import com.home.tribalapp.presentation.imageList.OnPhotoSelectedListener
import com.home.tribalapp.util.OnItemSelected

class FavoriteListAdapter(private val listener: OnItemSelected<Favorite>): RecyclerView.Adapter<FavoriteViewHolder>(){

    private var favoriteItems: List<Favorite> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite_image_list,
                parent,
                false
            ), listener
        )

    override fun getItemCount(): Int = favoriteItems.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(favoriteItems[position])

    fun setData(items: List<Favorite>) {
        this.favoriteItems = items
        notifyDataSetChanged()
    }

}