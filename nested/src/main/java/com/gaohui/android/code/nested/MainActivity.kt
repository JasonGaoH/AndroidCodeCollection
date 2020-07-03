package com.gaohui.android.code.nested

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val stringList: MutableList<String> =
        ArrayList()
    private val fragmentList: MutableList<Fragment> =
        ArrayList()
    private val mDataList = ArrayList<String>()
    private val strArray =
        arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门")

    @Synchronized
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        val indexPagerAdapter = IndexPagerAdapter(supportFragmentManager, stringList, fragmentList)
        view_pager.adapter = indexPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

    private fun initViews() {
        initData()
    }

    private fun initData() {
        stringList.addAll(strArray)
        strArray.forEach {
            fragmentList.add(ChildFragment.newInstance())
        }
    }

    internal inner class IndexPagerAdapter(
        fm: FragmentManager,
        private val titleList: List<String>,
        fragmentList: List<Fragment?>?
    ) :
        FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return titleList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}
