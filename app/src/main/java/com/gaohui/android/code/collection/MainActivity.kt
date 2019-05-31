package com.gaohui.android.code.collection

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gaohui.android.code.collection.view.CustomAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mDataList = ArrayList<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        parentRecyclerView.layoutManager = layoutManager

        val customAdapter =  CustomAdapter(mDataList)
        customAdapter.setFragmentManager(supportFragmentManager)
        parentRecyclerView.adapter = customAdapter

        for (i in 0..5) {
            mDataList.add("text: $i")
        }
        mDataList.add(1)

        parentRecyclerView.adapter?.notifyDataSetChanged()
    }
}
