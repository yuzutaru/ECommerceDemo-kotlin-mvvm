package com.yuzu.ecom.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yuzu.ecom.databinding.FragmentFeedBinding

/**
 * Created by Yustar Pramudana on 04/03/2021
 */
class FeedFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}