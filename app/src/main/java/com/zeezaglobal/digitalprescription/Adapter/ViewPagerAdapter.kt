package com.zeezaglobal.digitalprescription.Adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zeezaglobal.digitalprescription.Fragments.HomeFragment
import com.zeezaglobal.digitalprescription.Fragments.PrescriptionFragment
import com.zeezaglobal.digitalprescription.Fragments.SettingsFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> PrescriptionFragment()
            else -> SettingsFragment()
        }
    }
}