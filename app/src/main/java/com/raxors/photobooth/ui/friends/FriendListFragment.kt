package com.raxors.photobooth.ui.friends

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentFriendsBinding
import com.raxors.photobooth.ui.friends.adapter.FriendListAdapter
import com.raxors.photobooth.ui.friends.incoming.IncomingFragment
import com.raxors.photobooth.ui.friends.outgoing.OutgoingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FriendListFragment : BaseFragment<FriendListViewModel, FragmentFriendsBinding>(
    FragmentFriendsBinding::inflate
) {

    override val viewModel by viewModels<FriendListViewModel>()

    val adapter by lazy {
        FriendListAdapter(removeFriend = { user ->
            user.username?.let {
                showAlertDialog(
                    { viewModel.removeFriend(user) },
                    { },
                    it
                )
            }
        })
    }

    fun showAlertDialog(ok: () -> Unit, cancel: () -> Unit, userName: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить из друзей?")
        builder.setMessage("Вы точно хотите удалить пользователя $userName из друзей?")
        builder.setPositiveButton("Да") { _, _ -> ok() }
        builder.setNegativeButton("Отмена") { _, _ -> cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun initView() {
        with(binding) {
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
            tvSearch.setOnClickListener {

            }
            btnIncoming.setOnClickListener {
                val incomingDialog = IncomingFragment()
                incomingDialog.show(parentFragmentManager, "TAG")
            }
            btnOutgoing.setOnClickListener {
                val outgoingDialog = OutgoingFragment()
                outgoingDialog.show(parentFragmentManager, "TAG")
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
            removedFriend.observe(viewLifecycleOwner) {
                adapter.refresh()
            }
        }
    }

}