package com.gaohui.android.code.nested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_child.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [ChildFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildFragment : Fragment() {

    private val mDataList = ArrayList<Any>()

    private val strArray =
        arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门","附近","美妆","图片", "段子", "精华", "热门","附近","美妆")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childRecyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        childRecyclerView.adapter = RecyclerViewAdapter(mDataList)
        mDataList.add(Category().apply { dataList = ArrayList(strArray.asList()) })
        for (i in 0..100) {
            mDataList.add("child: $i")
        }

        childRecyclerView.adapter?.notifyDataSetChanged()

    }

    companion object {
        @JvmStatic
        fun newInstance() = ChildFragment()
    }
}