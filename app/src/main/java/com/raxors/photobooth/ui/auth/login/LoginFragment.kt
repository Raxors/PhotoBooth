package com.raxors.photobooth.ui.auth.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raxors.photobooth.R
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.data.model.request.LoginRequest
import com.raxors.photobooth.databinding.FragmentLoginBinding
import com.raxors.photobooth.utils.Security.sha256
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    override val viewModel by viewModels<LoginViewModel>()

    override fun initView() {
        with(binding) {
            ivBack.setOnClickListener {
                back()
            }
            btnLogin.setOnClickListener {
                viewModel.login(
                    LoginRequest(
                        etUsername.text.toString(),
                        etPassword.text.toString().sha256()
                    )
                )
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it.accessToken.isNotBlank()) {
                findNavController().navigate(R.id.main_dest)
            }
        }
    }

}