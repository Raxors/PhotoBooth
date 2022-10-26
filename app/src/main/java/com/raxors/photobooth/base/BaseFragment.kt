package com.raxors.photobooth.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM: BaseViewModel, VB: ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    abstract val viewModel: VM

    private lateinit var _binding: VB
    val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
//        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    abstract fun initView()
    open fun initViewModel() {
        viewModel.baseScreenState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseScreenState.Logout -> {

                }
                is BaseScreenState.Error -> {}
                is BaseScreenState.Loading -> {}
            }
        }
    }

    open fun back() {
        requireActivity().onBackPressed()
    }

    fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 0)
    }

}