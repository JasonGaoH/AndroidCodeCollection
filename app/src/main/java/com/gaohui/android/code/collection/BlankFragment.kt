package com.gaohui.android.code.collection

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gaohui.android.code.collection.view.CustomAdapter
import kotlinx.android.synthetic.main.fragment_blank.*
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import android.support.v7.widget.StaggeredGridLayoutManager
import com.gaohui.android.code.collection.view.ChildRecyclerView


class BlankFragment : Fragment() {

    private val mDataList = ArrayList<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    fun getChildRecyclerView():ChildRecyclerView {
        return childRecyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        childRecyclerView.layoutManager = layoutManager

        context?.let {
            childRecyclerView.adapter = CustomAdapter(mDataList)
        }


        for (i in 0..10) {
            mDataList.add("child: $i")
        }

        childRecyclerView.adapter?.notifyDataSetChanged()

        childRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)


                val z = getLastVisibleItem(childRecyclerView) >= getTotalItemCount(childRecyclerView) + -4
                if (newState == SCROLL_STATE_IDLE && z) {
                    loadData()
                }

            }
        })
    }

    private fun loadData() {
        for (i in 0..5) {
            mDataList.add("load more child: $i")
        }
        childRecyclerView.adapter?.notifyItemRangeChanged(mDataList.size-5,mDataList.size)
    }

    fun getTotalItemCount(childRecyclerView:RecyclerView): Int {
        return childRecyclerView.adapter?.itemCount?:-1
    }

    fun getFirstVisibleItem(childRecyclerView:RecyclerView): Int {
        val layoutManager = childRecyclerView.layoutManager
        if (layoutManager != null && layoutManager is StaggeredGridLayoutManager) {
            val iArr = IntArray(2)
            (layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(iArr)
            return if (iArr[0] > iArr[1]) iArr[1] else iArr[0]
        } else return if (layoutManager == null || layoutManager !is GridLayoutManager) {
            -1
        } else {
            (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        }
    }

    fun getLastVisibleItem(childRecyclerView:RecyclerView): Int {
        val layoutManager = childRecyclerView.layoutManager
        if (layoutManager != null && layoutManager is StaggeredGridLayoutManager) {
            val iArr = IntArray(2)
            (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(iArr)
            return if (iArr[0] > iArr[1]) iArr[0] else iArr[1]
        } else return if (layoutManager == null || layoutManager !is GridLayoutManager) {
            -1
        } else {
            (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}
