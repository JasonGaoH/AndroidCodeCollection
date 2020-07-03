package com.gaohui.android.code.nested

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private var dataSet: ArrayList<Any>
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
        return SimpleTextViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        if(holder is SimpleTextViewHolder) {
            holder.mTv.text = (dataSet[pos] as String)
        }
    }

    fun setDataList(dataSet: ArrayList<Any>) {
        this.dataSet = dataSet
    }

    override fun getItemCount() = dataSet.size

    private inner class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mTv: TextView = itemView.findViewById(R.id.textView) as TextView

    }

}