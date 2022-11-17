package com.raxors.photobooth.ui.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.raxors.photobooth.R
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding>(
    FragmentAuthBinding::inflate
) {
    override val viewModel by viewModels<AuthViewModel>()

    //Google
    private var googleSignInClient: GoogleSignInClient? = null

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    account?.idToken?.let {
                        viewModel.loginGoogle(it)
                    }
                } catch (e: ApiException) {
                    Log.e("AUTH_FRAGMENT", "got exc in google sign in : $e")
                }
            }
        }

    override fun initView() {
        initSocialClients()
        with(binding) {
            btnRegistration.setOnClickListener {
                findNavController().navigate(R.id.registration_dest)
            }

            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.login_dest)
            }
            btnLoginGoogle.setOnClickListener {
                googleSignInClient?.signOut()
                resultLauncher.launch(googleSignInClient?.signInIntent)
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

    private fun initSocialClients() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.google_client_id_my))
            .requestServerAuthCode(resources.getString(R.string.google_client_id_my))
            .requestEmail()
            .build()
        activity?.let {
            googleSignInClient = GoogleSignIn.getClient(it, gso)
            googleSignInClient?.signOut()
        } ?: Log.d("AUTH_FRAGMENT", "GOT NULL ACTIVITY!")
    }
}