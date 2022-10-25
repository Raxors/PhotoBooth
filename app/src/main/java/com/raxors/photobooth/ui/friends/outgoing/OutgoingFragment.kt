package com.raxors.photobooth.ui.friends.outgoing

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.raxors.photobooth.base.BaseBottomSheetDialog
import com.raxors.photobooth.databinding.FragmentIncomingBinding
import com.raxors.photobooth.databinding.FragmentOutgoingBinding
import com.raxors.photobooth.ui.friends.incoming.adapter.IncomingListAdapter
import com.raxors.photobooth.ui.friends.outgoing.adapter.OutgoingListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OutgoingFragment: BaseBottomSheetDialog<OutgoingViewModel, FragmentOutgoingBinding>(
    FragmentOutgoingBinding::inflate,
    BottomSheetBehavior.STATE_HALF_EXPANDED
) {

    override val viewModel by viewModels<OutgoingViewModel>()

    private val adapter by lazy {
        OutgoingListAdapter(
            declineRequest = {
                viewModel.removeFriend(it)
            }
        )
    }

    override fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvOutgoing.layoutManager = layoutManager
            rvOutgoing.adapter = adapter
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                getOutgoingList().observe(viewLifecycleOwner) {
                    adapter.submitData(lifecycle, it)
//                    binding.refreshLayout.isRefreshing = false
                }
            }
            removedFriend.observe(viewLifecycleOwner) {
                adapter.refresh()
            }
        }
    }
}