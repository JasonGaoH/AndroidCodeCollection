package com.gaohui.android.code.collection.view

import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class ChildRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    fun isTop(): Boolean {
        return !canScrollVertically(-1)
    }

}