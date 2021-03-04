package com.yuzu.ecom.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuzu.ecom.view.fragment.ChartFragment
import com.yuzu.ecom.view.fragment.FeedFragment
import com.yuzu.ecom.view.fragment.HistoryFragment
import com.yuzu.ecom.view.fragment.HomeFragment

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class MainMenuViewPagerAdapter(fragment: Fragment, private val titles: Array<String>) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment()
            1 -> return FeedFragment()
            2 -> return ChartFragment()
            3 -> return HistoryFragment()
        }
        return HomeFragment()
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}