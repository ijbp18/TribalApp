package com.home.tribalapp.presentation.imageList.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.home.tribalapp.R
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.presentation.imageList.OnPhotoSelectedListener
import com.home.tribalapp.util.loadUrl
import com.squareup.picasso.Picasso
import com.unsplash.pickerandroid.photopicker.presentation.AspectRatioImageView
import kotlinx.android.synthetic.main.item_image_list.view.*

/**
 * The photos recycler view adapter.
 * This is using the Android paging library to display an infinite list of photos.
 */
class ImageListAdapter constructor(context: Context) :
    PagedListAdapter<Image, ImageListAdapter.PhotoViewHolder>(COMPARATOR) {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    private val mSelectedIndexes = ArrayList<Int>()

    private val mSelectedImages = ArrayList<Image>()

    private var mOnPhotoSelectedListener: OnPhotoSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(mLayoutInflater.inflate(R.layout.item_image_list, parent, false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        // item
        getItem(position)?.let { photo ->
            // image
            holder.imageView.aspectRatio = photo.height.toDouble() / photo.width.toDouble()
            holder.itemView.setBackgroundColor(Color.parseColor(photo.color))

            holder.imageView.loadUrl(photo.urls.small)
            // photograph name
            holder.txtView.text = photo.user.name
            //like's number image
            holder.txtLike.text = photo.likes.toString()

            holder.profileImageView.loadUrl(photo.user.profile_image.medium)

            // click listener
            holder.itemView.setOnClickListener {
                // selected index(es) management
                if (mSelectedIndexes.contains(holder.adapterPosition)) {
                    mSelectedIndexes.remove(holder.adapterPosition)
                } else {
                    mSelectedIndexes.clear()
                    mSelectedIndexes.add(holder.adapterPosition)
                }
                mOnPhotoSelectedListener?.onPhotoSelected(photo)

            }

            holder.ivFavorite.setOnClickListener {
                mOnPhotoSelectedListener?.onPhotoFavoriteSelected(photo)
                photo.isSelected = !photo.isSelected
                if(photo.isSelected)
                   holder.ivFavorite.setImageResource( R.drawable.ic_favorite_full)
                else
                    holder.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite_empty)
            }

        }
    }

    /**
     * Getter for the selected images.
     */
    fun getImages(): ArrayList<Image> {
        mSelectedImages.clear()
        for (index in mSelectedIndexes) {
            currentList?.get(index)?.let {
                mSelectedImages.add(it)
            }
        }
        return mSelectedImages
    }

    fun clearSelection() {
        mSelectedImages.clear()
        mSelectedIndexes.clear()
    }

    fun setOnImageSelectedListener(onPhotoSelectedListener: OnPhotoSelectedListener) {
        mOnPhotoSelectedListener = onPhotoSelectedListener
    }

    companion object {
        // diff util comparator
        val COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem == newItem
        }
    }

    /**
     * UnsplashPhoto view holder.
     */
    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: AspectRatioImageView = view.item_unsplash_photo_image_view
        val txtView: TextView = view.item_unsplash_photo_text_view
        val txtLike: TextView = view.txtLikeImage
        val profileImageView: ImageView = view.item_user_profile_image_view
        val ivFavorite: ImageView = view.item_favorite_image_view
    }
}