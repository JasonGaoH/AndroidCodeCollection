package com.gaohui.android.code.collection.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.gaohui.android.code.collection.HomeFlingHelper
import com.gaohui.android.code.collection.utils.DpiUtil


class ChildRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    var startFling: Boolean = false
    var totalDy: Int = 0
    private var velocityY: Int = 0

    private val mFlingHelper: HomeFlingHelper = HomeFlingHelper(context)

//    private var mParentRecyclerView:ParentRecyclerView? = null

    private var mMaxDistance: Int = 0

    init {
        mMaxDistance = mFlingHelper.getVelocityByDistance((DpiUtil.getHeight(context) * 4).toDouble())
        addOnScrollListener(object :OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    dispatchParentFling()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (startFling) {
                    totalDy = 0
                    startFling = false
                }
                totalDy += dy
            }
        })
    }

    private fun findParentRecyclerView():ParentRecyclerView? {
        var parentView = parent
        while ((parentView is ParentRecyclerView).not()) {
            parentView = parentView.parent
        }
        return parentView as? ParentRecyclerView
    }

    private fun dispatchParentFling() {
        val mParentRecyclerView = findParentRecyclerView()
        if (mParentRecyclerView != null) {
            if (isTop() && velocityY != 0) {
                val splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY)
                if (splineFlingDistance > (Math.abs(totalDy).toDouble())) {
                    mParentRecyclerView.fling(0, -mFlingHelper.getVelocityByDistance(splineFlingDistance + totalDy.toDouble()))
                }
            }
            totalDy = 0
            velocityY = 0
        }
    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent?): Boolean {
        if (motionEvent != null && motionEvent.action == MotionEvent.ACTION_DOWN) {
            velocityY = 0
        }
        return super.dispatchTouchEvent(motionEvent)
    }

    override fun fling(velX: Int, velY: Int): Boolean {
        if (!isAttachedToWindow) {
            return false
        }

        var tempVelY = velY
        val abs = Math.abs(tempVelY)
        if (mMaxDistance in 8889..(abs - 1)) {
            tempVelY = abs * mMaxDistance / tempVelY
        }

        Log.d("gaohui","fling: $tempVelY")
        val fling = super.fling(velX, tempVelY)
        Log.d("gaohui","has fling: $fling")
        if (!fling || tempVelY >= 0) {

            velocityY = 0
        } else {
            startFling = true
            velocityY = tempVelY
        }
        return fling
    }


    fun isTop(): Boolean {
        return !canScrollVertically(-1)
    }

}