package com.raxors.photobooth.ui.auth.registration

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raxors.photobooth.R
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.data.model.request.RegistrationRequest
import com.raxors.photobooth.databinding.FragmentRegistrationBinding
import com.raxors.photobooth.utils.Security.sha256
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>(
    FragmentRegistrationBinding::inflate
) {

    override val viewModel by viewModels<RegistrationViewModel>()

    override fun initView() {
        with(binding) {
            ivBack.setOnClickListener {
                back()
            }
            btnLogin.setOnClickListener {
                viewModel.registration(
                    RegistrationRequest(
                        etUsername.text.toString(),
                        etPassword.text.toString().sha256(),
                        etEmail.text.toString()
                    )
                )
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.regResult.observe(viewLifecycleOwner) {
            if (it.accessToken.isNotBlank()) {
                findNavController().navigate(R.id.main_dest)
            }
        }
    }

}