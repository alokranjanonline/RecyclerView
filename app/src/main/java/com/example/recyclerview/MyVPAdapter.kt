package com.example.recyclerview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyVPAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->ProfileFragment()
            1->ChatFragment()
            else->ProfileFragment()
        }
    }

}