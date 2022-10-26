package com.raxors.photobooth.ui.search

import androidx.fragment.app.viewModels
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentSearchBinding
import com.raxors.photobooth.ui.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment<SearchViewModel, FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {
    override val viewModel by viewModels<SearchViewModel>()

    private val adapter by lazy {
        SearchAdapter(
            addFriend = {
                it.id?.let { userId -> viewModel.addFriend(userId) }
            }
        )
    }

    override fun initView() {
        with(binding) {
            etSearch.requestFocus()
            etSearch.showKeyboard()

            rvFriendList.adapter = adapter

            btnSearch.setOnClickListener {
                viewModel.searchUser(etSearch.text.toString())
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        with(viewModel) {
            userList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
            isAdded.observe(viewLifecycleOwner) {
                if (it)
                    back()
            }
        }
    }
}