package com.raxors.photobooth.ui.photos.detail

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentPhotoBinding
import com.raxors.photobooth.di.BASE_PHOTO_URL
import com.raxors.photobooth.utils.ARGS_ID_USER
import com.raxors.photobooth.utils.ARGS_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment: BaseFragment<PhotoDetailViewModel, FragmentPhotoBinding>(
    FragmentPhotoBinding::inflate
) {
    override val viewModel by viewModels<PhotoDetailViewModel>()

    override fun initView() {
        val userId = arguments?.getString(ARGS_ID_USER)
        val photoPath = arguments?.getString(ARGS_URL)
        userId?.let { viewModel.getProfile(userId) }
        photoPath?.let {
            Glide.with(this).load(BASE_PHOTO_URL + it).into(binding.ivPhoto)
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            profile.observe(viewLifecycleOwner) {
                binding.tvName.text = it.username
            }
        }
    }
}