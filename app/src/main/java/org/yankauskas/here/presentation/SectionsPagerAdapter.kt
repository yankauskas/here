package org.yankauskas.here.presentation

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.yankauskas.here.R
import org.yankauskas.here.presentation.list.ListFragment
import org.yankauskas.here.presentation.map.NearbyMapFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = arrayListOf(NearbyMapFragment(), ListFragment())

    override fun getItem(position: Int) = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(TAB_TITLES[position])
    }

    override fun getCount() = 2
}