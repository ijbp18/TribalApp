package com.home.tribalapp.presentation.imageList

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.home.tribalapp.R
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.di.Injector
import com.home.tribalapp.presentation.imageList.adapter.ImageListAdapter
import com.home.tribalapp.presentation.imageList.viewmodel.ImageListViewModel
import com.home.tribalapp.util.Constants.SELECTED_USER_INTENT
import com.home.tribalapp.util.hide
import com.home.tribalapp.util.show
import com.home.tribalapp.util.showToast
import kotlinx.android.synthetic.main.fragment_image_list.*

class ImageListFragment : Fragment(), OnPhotoSelectedListener, SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    private lateinit var mAdapter: ImageListAdapter
    private val viewModel: ImageListViewModel by activityViewModels { Injector.createPickerViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initAdapter()
        initUi()
        setHasOptionsMenu(true)
    }

    private fun initAdapter() {
        mAdapter = ImageListAdapter(requireContext())
        mAdapter.setOnImageSelectedListener(this)
    }

    private fun initUi() {
        recycler_view_image.setHasFixedSize(true)
        recycler_view_image.itemAnimator = null
        recycler_view_image.adapter = mAdapter
    }

    private fun setupViewModel() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            activity?.showToast(requireContext(), getString(R.string.error_message))
        })
        viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
            activity?.showToast(requireContext(), it)

        })
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null && it) unsplash_picker_progress_bar_layout.show()
            else unsplash_picker_progress_bar_layout.hide()
        })
        viewModel.photosLiveData.observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) unsplash_picker_no_result_text_view.show()
            else unsplash_picker_no_result_text_view.hide()
            mAdapter.submitList(it)
        })
    }

    override fun onPhotoSelected(item: Image) {
        val bundle = bundleOf(SELECTED_USER_INTENT to item)
        view?.findNavController()?.navigate(R.id.userProfileFragment, bundle)
    }

    override fun onPhotoFavoriteSelected(item: Image) {
        activity?.showToast(requireContext(), "Guardó correctamente")
        viewModel.saveFavoriteItem(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                view?.findNavController()?.navigate(R.id.favoriteFragment)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                false
            }
        }
    }

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String): Boolean {
        if (newText.length > 2) viewModel.bindSearch(newText)
        else viewModel.retrieveImages()
        return true
    }

    override fun onClose(): Boolean {
        viewModel.retrieveImages()
        return false
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveImages()
    }
}