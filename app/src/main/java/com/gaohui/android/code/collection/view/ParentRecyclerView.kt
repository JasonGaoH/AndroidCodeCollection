package com.gaohui.android.code.collection.view

import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.gaohui.android.code.collection.BlankFragment
import com.gaohui.android.code.collection.HomeFlingHelper
import kotlinx.android.synthetic.main.fragment_blank.view.*
import android.support.v4.view.ViewCompat.canScrollVertically
import android.support.v4.view.MotionEventCompat.getActionMasked
import android.view.MotionEvent




class ParentRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr),NestedScrollingParent {

    private var mMaxDistance: Int = 0

    private val mHomeFlingHelper:HomeFlingHelper = HomeFlingHelper(context)

    private var lastY: Float = 0.toFloat()
    init {
        //mMaxDistance = mHomeFlingHelper.getVelocityByDistance()
    }



    private fun isScrollEnd(): Boolean {
        return !canScrollVertically(1)
    }

    private fun findNestedScrollingChildView():ChildRecyclerView? {
        val adapter =  adapter
        if((adapter is CustomAdapter).not()) {
            return null
        }
        val categoryView = (adapter as CustomAdapter).getCategoryView() ?: return null

        val childRecyclerView = categoryView.fragmentList[categoryView.mViewPager.currentItem]
        return (childRecyclerView as BlankFragment).getChildRecyclerView()
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val z = false
        if (this.lastY == 0.0f) {
            this.lastY = motionEvent.y
        }
        if (isScrollEnd()) {
            val childRecyclerView =   findNestedScrollingChildView()
            if (childRecyclerView != null) {
//                this.canScrollVertically.set(z)
                childRecyclerView.scrollBy(0,(this.lastY - motionEvent.y).toInt())
            }
        }
//        if (motionEvent.actionMasked == 1) {
//            this.canScrollVertically.set(true)
//        }
        this.lastY = motionEvent.y
        //this.awG = z
        try {
            return super.onTouchEvent(motionEvent)
        } catch (e: Exception) {
            e.printStackTrace()
//            zi()
            return z
        }

    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return target is ChildRecyclerView
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {

        val childRecyclerView = findNestedScrollingChildView()
        val isEnd = dy > 0 && !isScrollEnd()
        val handle = !(dy >= 0 || childRecyclerView == null || !childRecyclerView.isTop())

        if(isEnd || handle) {
            if(!handle || !isScrollEnd()) {
                scrollBy(0, dy)
                consumed[1] = dy
                return
            }
            consumed[1] = dy
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {

    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        //        val z: Boolean
//        val zn = zn()
//        val z2 = f2 > 0.0f && !isScrollEnd()
//        if (f2 >= 0.0f || zn == null || !zn!!.isTop()) {
//            z = false
//        } else {
//            z = true
//        }
//        if (!z2 && !z) {
//            return false
//        }
//        this.awG = false
//        fling(0, f2 as Int)
//        return true

        val flag =  velocityY > 0.0f && isScrollEnd().not()
        val handle = !(velocityY > 0.0f || childRecyclerView == null || !childRecyclerView.isTop())
        if(!flag && !handle) {
            return false
        }

        //this.awG = false
        fling(0, velocityY.toInt())

        return true
    }

    override fun onStopNestedScroll(child: View) {
    }


    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return true
    }


    override fun getNestedScrollAxes(): Int {
        return 0
    }


//    override fun fling(i: Int, i2: Int): Boolean {
//        var i2 = i2
//        val abs = Math.abs(i2)
//        if (mMaxDistance > 8888 && abs > mMaxDistance) {
//            i2 = abs * mMaxDistance / i2
//        }
//        val fling = super.fling(i, i2)
//        if (!fling || i2 <= 0) {
//            this.velocityY = 0
//        } else {
//            this.startFling = true
//            this.velocityY = i2
//        }
//        return fling
//    }

}