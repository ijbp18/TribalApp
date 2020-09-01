package com.home.tribalapp.presentation.favoriteImages

import android.graphics.fonts.FontVariationAxis
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.home.tribalapp.R
import com.home.tribalapp.data.OperationResult
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.di.Injector
import com.home.tribalapp.domain.model.Favorite
import com.home.tribalapp.presentation.favoriteImages.adapter.FavoriteListAdapter
import com.home.tribalapp.presentation.favoriteImages.viewmodel.FavoriteListViewModel
import com.home.tribalapp.presentation.imageList.OnPhotoSelectedListener
import com.home.tribalapp.util.OnItemSelected
import com.home.tribalapp.util.hide
import com.home.tribalapp.util.show
import com.home.tribalapp.util.showToast
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_user_profile.*

class FavoriteFragment : Fragment() {


    private val viewModel: FavoriteListViewModel by activityViewModels { Injector.createFavoriteViewModelFactory() }

    private val onItemSelectedListener: OnItemSelected<Favorite> =
        object :
            OnItemSelected<Favorite> {
            override fun onItemSelected(item: Favorite) {
                viewModel.removeFavorite(item)
            }
        }

    private val mAdapter: FavoriteListAdapter = FavoriteListAdapter(onItemSelectedListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.retrieveFavoriteItems()
        initUI()
    }

    private fun initUI() {
        rv_favorite_items.setHasFixedSize(true)
        rv_favorite_items.itemAnimator = null
        rv_favorite_items.adapter = mAdapter
    }

    private fun setupViewModel() {

        viewModel.photosLiveData.observe(viewLifecycleOwner, Observer { operation ->
            when(operation){
                is OperationResult.Loading -> {
                    progress_favorite.show()
                }
                is OperationResult.Success -> {
                    operation.data?.let { favorites ->
                        if (favorites.isNotEmpty()) {
                            progress_favorite.hide()
                            showFavorites(favorites)
                        } else {
                            progress_favorite.hide()
                            no_favorite_text_view.show()
                            rv_favorite_items.hide()
                        }
                    }
                }
            }

//            rv_user_photos.adapter = mAdapter
        })
    }

    private fun showFavorites(favorites: List<Favorite>) {
        mAdapter.setData(favorites)
    }





}