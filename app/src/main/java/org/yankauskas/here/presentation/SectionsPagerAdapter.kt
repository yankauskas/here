package org.yankauskas.here.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.yankauskas.here.R
import org.yankauskas.here.presentation.list.ListFragment
import org.yankauskas.here.presentation.map.MapFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val fragmentList = arrayListOf(MapFragment.newInstance(1), ListFragment.newInstance(2))

    override fun getItem(position: Int) = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount() = 2
}