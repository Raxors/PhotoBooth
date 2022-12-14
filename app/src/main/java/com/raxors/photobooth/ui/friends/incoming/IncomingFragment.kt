package com.raxors.photobooth.ui.friends.incoming

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.raxors.photobooth.base.BaseBottomSheetDialog
import com.raxors.photobooth.databinding.FragmentIncomingBinding
import com.raxors.photobooth.ui.friends.incoming.adapter.IncomingListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IncomingFragment: BaseBottomSheetDialog<IncomingViewModel, FragmentIncomingBinding>(
    FragmentIncomingBinding::inflate,
    BottomSheetBehavior.STATE_HALF_EXPANDED
) {

    override val viewModel by viewModels<IncomingViewModel>()

    private val adapter by lazy {
        IncomingListAdapter(
            addFriend = {
                viewModel.addFriend(it)
            },
            declineRequest = {
                viewModel.removeFriend(it)
            }
        )
    }

    override fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvIncoming.layoutManager = layoutManager
            rvIncoming.adapter = adapter
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                getIncomingList().observe(viewLifecycleOwner) {
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