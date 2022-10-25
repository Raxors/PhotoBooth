package com.raxors.photobooth.ui.search

import androidx.fragment.app.viewModels
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentSearchBinding

class SearchFragment: BaseFragment<SearchViewModel, FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {
    override val viewModel by viewModels<SearchViewModel>()

    override fun initView() {
        with(binding) {
            etSearch.requestFocus()
        }
    }
}