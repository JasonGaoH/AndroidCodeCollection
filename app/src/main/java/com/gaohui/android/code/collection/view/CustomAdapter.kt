package com.gaohui.android.code.collection.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gaohui.android.code.collection.R


class CustomAdapter(
    private val dataSet: ArrayList<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_TEXT = 0
    private val TYPE_CATEGORY = 1


    override fun getItemViewType(position: Int): Int {
        if(dataSet[position] is String) {
            return TYPE_TEXT
        } else {
            return TYPE_CATEGORY
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item_text, viewGroup, false)
        Log.d("gaohui","onCreateViewHolder")
        return SimpleTextViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        if(holder is SimpleTextViewHolder) {
            Log.d("gaohui","onBindViewHolder")
            holder.mTv.text = (dataSet[pos] as String)
        }

    }

    override fun getItemCount() = dataSet.size

    private inner class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val mTv: TextView = itemView.findViewById(R.id.textView) as TextView

    }

}