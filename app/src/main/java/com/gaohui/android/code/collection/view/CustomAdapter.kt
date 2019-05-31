package com.gaohui.android.code.collection.view

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gaohui.android.code.collection.BlankFragment
import com.gaohui.android.code.collection.R


class CustomAdapter(
    private val dataSet: ArrayList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_TEXT = 0
    private val TYPE_CATEGORY = 1

    private var mCategoryView:SimpleCategoryViewHolder? = null


    override fun getItemViewType(position: Int): Int {
        if(dataSet[position] is String) {
            return TYPE_TEXT
        } else {
            return TYPE_CATEGORY
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_TEXT) {
            val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item_text, viewGroup, false)
            return SimpleTextViewHolder(v)
        } else {
            val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_category_text, viewGroup, false)
            val holder = SimpleCategoryViewHolder(v)
            mCategoryView = holder
            return holder
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        if(holder is SimpleTextViewHolder) {
            holder.mTv.text = (dataSet[pos] as String)
        } else if(holder is SimpleCategoryViewHolder) {
            holder.bindData()
        }

    }

    override fun getItemCount() = dataSet.size

    fun getCategoryView():SimpleCategoryViewHolder? {
        return mCategoryView
    }

    var fm:FragmentManager? = null

    fun setFragmentManager(supportFragmentManager: FragmentManager?) {
        fm = supportFragmentManager

    }

    private inner class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val mTv: TextView = itemView.findViewById(R.id.textView) as TextView

    }

    inner class SimpleCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fragmentList = ArrayList<Fragment>()
        private val titleList = ArrayList<String>()

         val mTabLayout: TabLayout = itemView.findViewById(R.id.tabs) as TabLayout
         val mViewPager: ViewPager = itemView.findViewById(R.id.viewPager) as ViewPager

        fun bindData() {
            fragmentList.clear()
            titleList.clear()

            for (i in 0..5) {
                titleList.add("tab $i")
                fragmentList.add(BlankFragment.newInstance())
            }

            mViewPager.adapter = IndexPagerAdapter(fm!!,titleList,fragmentList)
            mTabLayout.setupWithViewPager(mViewPager)
        }

        internal inner class IndexPagerAdapter(fm: FragmentManager, private val titleList: List<String>, val fragmentList: List<Fragment>) : FragmentStatePagerAdapter(fm) {

            override fun getItem(position: Int): Fragment {
                return fragmentList.get(position)
            }

            override fun getCount(): Int {
                return titleList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titleList[position]
            }

        }
    }

}