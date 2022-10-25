package com.raxors.photobooth.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM: BaseViewModel, VB: ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    abstract fun initView()
    open fun initViewModel() {
        /*viewModel.baseScreenState.observe(viewLifecycleOwner) {
            println()
        }*/
    }

    open fun back() {
        requireActivity().onBackPressed()
    }

}