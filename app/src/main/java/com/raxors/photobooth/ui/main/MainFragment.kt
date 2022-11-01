package com.raxors.photobooth.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.raxors.photobooth.R
import com.raxors.photobooth.base.BaseFragment
import com.raxors.photobooth.databinding.FragmentMainBinding
import com.raxors.photobooth.ui.camera.CameraFragment
import com.raxors.photobooth.ui.friends.FriendListFragment
import com.raxors.photobooth.ui.photos.PhotoListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentViewModel, FragmentMainBinding>(
    FragmentMainBinding::inflate
) {

    override val viewModel by activityViewModels<MainFragmentViewModel>()

    private lateinit var viewPager: ViewPager2

    private val mOnNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.friends -> {
                viewPager.currentItem = 0
                return@OnItemSelectedListener true
            }
            R.id.main -> {
                viewPager.currentItem = 1
                return@OnItemSelectedListener true
            }
            R.id.history -> {
                viewPager.currentItem = 2
                return@OnItemSelectedListener true
            }
//            R.id.profile -> {
//                viewPager.currentItem = 3
//                return@OnItemSelectedListener true
//            }
            else -> return@OnItemSelectedListener false
        }
    }


    override fun initView() {
        with(binding) {
            viewPager = vpContainer
            val pagerAdapter = ScreenSlidePagerAdapter(this@MainFragment)
            viewPager.adapter = pagerAdapter
            topNavView.setOnItemSelectedListener(mOnNavigationItemSelectedListener)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> topNavView.menu.findItem(R.id.friends).isChecked = true
                        1 -> topNavView.menu.findItem(R.id.main).isChecked = true
                        2 -> topNavView.menu.findItem(R.id.history).isChecked = true
//                        3 -> topNavView.menu.findItem(R.id.profile).isChecked = true
                    }
                }
            })

            viewPager.setCurrentItem(1, false)
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        with(viewModel) {

        }
    }

    private inner class ScreenSlidePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment =
            when (position) {
                0 -> FriendListFragment()
                1 -> CameraFragment()
                else -> PhotoListFragment()
//                else -> ProfileFragment()
            }
    }

}