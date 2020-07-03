package com.gaohui.android.code.nested.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaohui.android.code.nested.R

internal class SimpleTextViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var textView: TextView = itemView.findViewById(R.id.textView)

}