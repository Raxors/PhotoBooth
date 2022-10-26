package com.raxors.photobooth.ui.profile

import androidx.fragment.app.viewModels
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentProfileBinding
import com.raxors.photobooth.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<ProfileViewModel, FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    override val viewModel by viewModels<ProfileViewModel>()

    override fun initView() {
        with (binding) {

            btnExit.setOnClickListener {
                (requireActivity() as MainActivity).viewModel.logout()
            }


        }
        viewModel.getProfile()
    }

    override fun initViewModel() {
        super.initViewModel()
    }

}