package com.raxors.photobooth.ui.friends

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentFriendsBinding
import com.raxors.photobooth.ui.friends.adapter.FriendListAdapter
import com.raxors.photobooth.ui.friends.incoming.IncomingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FriendListFragment: BaseFragment<FriendListViewModel, FragmentFriendsBinding>(
    FragmentFriendsBinding::inflate
) {

    override val viewModel by viewModels<FriendListViewModel>()

    val adapter by lazy {
        FriendListAdapter(
            addFriend = { userId ->
                viewModel.addFriend(userId)
            }
        )
    }

    override fun initView() {
        with (binding) {
            rvFriendList.layoutManager = LinearLayoutManager(requireContext())
            rvFriendList.adapter = adapter

            btnSearch.setOnClickListener {
//                viewModel.searchUser(etSearch.text.toString())
            }

//            ivClear.setOnClickListener {
//                etSearch.setText("")
//                /*adapter.submitList(vmMain.friendList.value?.map {
//                    it.copy(isFriend = true)
//                })*/
//            }

            btnIncoming.setOnClickListener {
                val incomingDialog = IncomingFragment()
                incomingDialog.show(parentFragmentManager, "TAG")
            }
            refreshLayout.setOnRefreshListener {
                adapter.refresh()
            }

        }
    }

    override fun initViewModel() {
        super.initViewModel()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                getFriends().observe(viewLifecycleOwner) {
                    adapter.submitData(lifecycle, it)
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
    }

}