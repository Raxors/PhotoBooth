package com.raxors.photobooth.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetDialog<VM: BaseViewModel, VB: ViewBinding>(
    private val inflate: Inflate<VB>,
    private val expandState: Int
) : BottomSheetDialogFragment() {

    abstract val viewModel: VM

    private var _binding: VB? = null
    val binding get() = _binding!!

    lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            behavior = BottomSheetBehavior.from(bottomSheetInternal!!)
//            behavior.isFitToContents = false
            behavior.halfExpandedRatio = 0.4f
            behavior.skipCollapsed = true
            behavior.state = expandState
        }
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
        viewModel.baseScreenState.observe(viewLifecycleOwner) {

        }
    }

}