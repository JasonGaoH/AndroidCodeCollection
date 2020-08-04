package com.gaohui.android.code.nested

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        //fix 解决ViewPager1滑动不连贯的问题
        isNestedScrollingEnabled = false
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        //fix 解决ViewPager2滑动不连贯的问题
        if(e.action == MotionEvent.ACTION_UP) {
            val viewParent: ViewParent? = parent
            if (viewParent != null && viewParent is RecyclerView) {
                viewParent.stopNestedScroll()
            }
        }
        return super.onTouchEvent(e)
    }
}