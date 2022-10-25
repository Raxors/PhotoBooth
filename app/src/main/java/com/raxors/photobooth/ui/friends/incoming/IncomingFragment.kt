package com.raxors.photobooth.ui.friends.incoming

import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.raxors.photobooth.base.BaseBottomSheetDialog
import com.raxors.photobooth.databinding.FragmentIncomingBinding
import com.raxors.photobooth.ui.friends.incoming.adapter.IncomingListAdapter

class IncomingFragment: BaseBottomSheetDialog<IncomingViewModel, FragmentIncomingBinding>(
    FragmentIncomingBinding::inflate,
    BottomSheetBehavior.STATE_HALF_EXPANDED
) {

    override val viewModel by viewModels<IncomingViewModel>()

    private val adapter by lazy {
        IncomingListAdapter(

        )
    }

    override fun initView() {

    }
}