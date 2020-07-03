package com.gaohui.android.code.collection

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import com.gaohui.android.code.collection.view.CustomAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 用来测试LinearLayoutManager预加载问题
 */
class MainActivity : AppCompatActivity() {

    private val mDataList = ArrayList<Any>()

    private val pagerSnapHelper = PagerSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        //全屏item，重写getExtraLayoutSpace可以实现预加载，提前创建下一个ViewHolder，并调用bind回调
//        recyclerView.layoutManager = object : LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false) {
//            override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
//                return 100
//            }
//        }
        pagerSnapHelper.attachToRecyclerView(recyclerView)
        val customAdapter =  CustomAdapter(mDataList)
        recyclerView.adapter = customAdapter
        for (i in 0..8) {
            mDataList.add("text: $i")
        }

        recyclerView?.adapter?.notifyDataSetChanged()
    }
}
