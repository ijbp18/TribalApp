package com.home.tribalapp.presentation.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.home.tribalapp.R
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.di.Injector
import com.home.tribalapp.presentation.activities.WebViewActivity
import com.home.tribalapp.presentation.imageList.OnPhotoSelectedListener
import com.home.tribalapp.presentation.userprofile.adapter.UserImageListAdapter
import com.home.tribalapp.presentation.favoriteImages.viewmodel.FavoriteListViewModel
import com.home.tribalapp.presentation.userprofile.viewmodel.UserImageListViewModel
import com.home.tribalapp.util.Constants.INSTAGRAM_URL
import com.home.tribalapp.util.Constants.SELECTED_USER_INTENT
import com.home.tribalapp.util.Constants.TWITTER_URL
import com.home.tribalapp.util.hide
import com.home.tribalapp.util.show
import com.home.tribalapp.util.showToast
import com.squareup.picasso.Picasso
import com.unsplash.pickerandroid.photopicker.presentation.PhotoShowActivity
import kotlinx.android.synthetic.main.fragment_user_profile.*


class UserProfileFragment : Fragment(), OnPhotoSelectedListener, View.OnClickListener {

    lateinit var imageSelected: Image
    private lateinit var mAdapter: UserImageListAdapter

    private val viewModel: UserImageListViewModel by activityViewModels { Injector.createPickerUserViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtra()
        setupViewModel()
        loadUserInfo()
        initAdapter()
        initUi()
        viewModel.retrieveUserData(imageSelected.user.username)
        listener()

    }

    private fun listener() {
        iv_twitter.setOnClickListener(this)
        iv_instagram.setOnClickListener(this)
    }

    private fun initUi() {
        rv_user_photos.setHasFixedSize(true)
        rv_user_photos.itemAnimator = null
        rv_user_photos.adapter = mAdapter
    }

    private fun setupViewModel() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            activity?.showToast(requireContext(), getString(R.string.error_message))
        })
        viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
            activity?.showToast(requireContext(), it)
        })
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it) progress_user_profile.show()
            else progress_user_profile.hide()
        })
        viewModel.photosLiveData.observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) no_result_text_view.show()
            else no_result_text_view.hide()
            mAdapter.submitList(it)
            rv_user_photos.adapter = mAdapter
        })
    }

    private fun loadUserInfo() {

        imageSelected.let {
            Picasso.get().load(it.user.profile_image.large).into(iv_user_profile)
            tv_profile_user.text = it.user.name
            if (it.user.bio.isNullOrEmpty()) tv_description_user.visibility = View.GONE
            else tv_description_user.text = it.user.bio
            tv_user_photos.text = it.user.total_photos.toString()
            tv_user_collections.text = it.user.total_collection.toString()
            tv_user_likes.text = it.user.total_likes.toString()
            if (it.user.location.isNullOrEmpty()) {
                tv_location.hide()
                iv_location.hide()
            } else tv_location.text = it.user.location

        }
    }

    private fun getExtra() {
        imageSelected = arguments?.getParcelable<Image>(SELECTED_USER_INTENT) as Image
    }

    private fun initAdapter() {
        mAdapter = UserImageListAdapter(requireContext())
        mAdapter.setOnImageSelectedListener(this)
    }

    override fun onPhotoSelected(item: Image) {
        startActivity(item.urls.regular?.let {
            PhotoShowActivity.getStartingIntent(
                requireContext(),
                it
            )
        })
    }

    override fun onPhotoFavoriteSelected(item: Image) {
        //Not necessary in this view
    }

    override fun onClick(view: View) {
        var urlType = ""
        when (view.id) {
            R.id.iv_twitter -> {
                urlType = if (imageSelected.user.twitter_username.isNullOrEmpty()) TWITTER_URL
                else TWITTER_URL.plus(imageSelected.user.twitter_username)
            }
            R.id.iv_instagram -> {
                urlType = if (imageSelected.user.instagram_username.isNullOrEmpty()) INSTAGRAM_URL
                else INSTAGRAM_URL.plus(imageSelected.user.instagram_username)
            }
        }

        startActivity(WebViewActivity.getStartingIntent(requireContext(), urlType))
    }

}