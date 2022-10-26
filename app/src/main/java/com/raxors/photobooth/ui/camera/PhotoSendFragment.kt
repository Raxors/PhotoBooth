package com.raxors.photobooth.ui.camera

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.insertHeaderItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentPhotoSendBinding
import com.raxors.photobooth.ui.camera.adapter.CheckAllModel
import com.raxors.photobooth.ui.camera.adapter.CheckFriendModel
import com.raxors.photobooth.ui.camera.adapter.FriendListPhotoSendAdapter
import com.raxors.photobooth.ui.main.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoSendFragment : BaseFragment<MainFragmentViewModel, FragmentPhotoSendBinding>(
    FragmentPhotoSendBinding::inflate
) {
    override val viewModel by activityViewModels<MainFragmentViewModel>()

    private val adapter by lazy {
        FriendListPhotoSendAdapter(
            checkAll = {
                checkAll()
            },
            checkFriend = {
                checkFriend(it)
            }
        )
    }

    private fun checkAll() {
        val friendList = adapter.snapshot()
        friendList.forEach {
            if (it is CheckFriendModel)
                it.isChecked = false
        }
        val all = friendList[0] as CheckAllModel
        all.isChecked = !all.isChecked
        adapter.notifyDataSetChanged()
    }

    private fun checkFriend(checkFriendModel: CheckFriendModel) {
        val all = adapter.snapshot()[0] as CheckAllModel
        all.isChecked = false
        adapter.notifyItemChanged(0)
        val index = adapter.snapshot().indexOf(checkFriendModel)
        val model = (adapter.snapshot()[index] as CheckFriendModel)
        model.isChecked = !model.isChecked
        adapter.notifyItemChanged(index)
    }

    override fun initView() {
        with(binding) {
            rvFriendList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            rvFriendList.adapter = adapter

            btnClose.setOnClickListener {
                back()
            }
            btnSendPhoto.setOnClickListener {
                if ((adapter.snapshot()[0] as CheckAllModel).isChecked)
                    viewModel.sendPhoto(listOf())
                else {
                    val checkedFriendsIds = adapter.snapshot()
                        .filter { it is CheckFriendModel && it.isChecked && it.user.id != null }
                        .map { (it as CheckFriendModel).user.id!! }
                    if (checkedFriendsIds.isNotEmpty())
                        viewModel.sendPhoto(checkedFriendsIds)
                    else
                        Toast.makeText(requireContext(), "Выберете получателей", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                getFriends().observe(viewLifecycleOwner) {
                    adapter.submitData(lifecycle, it.insertHeaderItem(item = CheckAllModel()))
                }
            }
            bitmap.observe(viewLifecycleOwner) {
                binding.ivPhoto.setImageBitmap(it)
            }
            isPhotoSended.observe(viewLifecycleOwner) {
                if (it)
                    back()
            }
        }
    }
}