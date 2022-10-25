package com.raxors.photobooth.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.raxors.photobooth.base.BaseBottomSheetDialog
import com.raxors.photobooth.data.model.response.PhotoResponse
import com.raxors.photobooth.databinding.FragmentPhotoListBinding
import com.raxors.photobooth.ui.photos.adapter.PhotoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PhotoListFragment: BaseBottomSheetDialog<PhotoListViewModel, FragmentPhotoListBinding>(
    FragmentPhotoListBinding::inflate,
    BottomSheetBehavior.STATE_HALF_EXPANDED
) {

    override val viewModel by viewModels<PhotoListViewModel>()

    private val adapter by lazy {
        PhotoListAdapter(
            openImage = { openImage(it) }
        )
    }

    private fun openImage(photo: PhotoResponse) {
//        viewModel.openPhoto(photo)
//        parentFragmentManager.commit {
//            setReorderingAllowed(true)
//            replace<PhotoDetailFragment>(R.id.photo_send_container, PhotoListFragment::class.simpleName)
//        }
    }

    override fun onResume() {
        super.onResume()
//        viewModel.getPhotoList()
    }

    override fun initView() {
        with(binding) {
            rvPhotoList.layoutManager = GridLayoutManager(context, 3)
            rvPhotoList.adapter = adapter
//            it.rvPhotoList.addItemDecoration(SpacesItemDecoration(8))
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                getPhotoList().observe(viewLifecycleOwner) {
                    adapter.submitData(lifecycle, it)
//                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
    }

}