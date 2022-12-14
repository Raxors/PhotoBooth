package com.raxors.photobooth.ui.photos

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.raxors.photobooth.R
import com.raxors.photobooth.base.BaseBottomSheetDialog
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.databinding.FragmentPhotoListBinding
import com.raxors.photobooth.ui.photos.adapter.PhotoListAdapter
import com.raxors.photobooth.utils.ARGS_ID_USER
import com.raxors.photobooth.utils.ARGS_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PhotoListFragment: BaseFragment<PhotoListViewModel, FragmentPhotoListBinding>(
    FragmentPhotoListBinding::inflate
) {

    override val viewModel by viewModels<PhotoListViewModel>()

    private val adapter by lazy {
        PhotoListAdapter(
            openImage = { openImage(it) }
        )
    }

    private fun openImage(photo: PhotoResponse) {
        val bundle = Bundle()
        bundle.putString(ARGS_ID_USER, photo.ownerId)
        bundle.putString(ARGS_URL, photo.path)
        findNavController().navigate(R.id.photo_detail_dest, bundle)
    }

    override fun initView() {
        with(binding) {
            rvPhotoList.layoutManager = GridLayoutManager(context, 3)
            rvPhotoList.adapter = adapter
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                getPhotoList().observe(viewLifecycleOwner) {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }

}