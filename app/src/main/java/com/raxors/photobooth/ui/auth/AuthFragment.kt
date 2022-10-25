package com.raxors.photobooth.ui.auth

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raxors.photobooth.R
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding>(
    FragmentAuthBinding::inflate
) {
    override val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO костыль потом исправить
//        if (viewModel.isLogin()) {
//            findNavController().navigate(R.id.main_dest)
//        }
    }

    override fun initView() {
        with(binding) {
            btnRegistration.setOnClickListener {
                findNavController().navigate(R.id.registration_dest)
            }

            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.login_dest)
            }
        }
    }
}