package com.gaohui.android.code.collection.view

import android.content.Context
import android.support.v4.view.NestedScrollingParent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.gaohui.android.code.collection.BlankFragment
import com.gaohui.android.code.collection.HomeFlingHelper
import kotlinx.android.synthetic.main.fragment_blank.view.*
import android.view.MotionEvent
import com.gaohui.android.code.collection.utils.DpiUtil
import java.util.concurrent.atomic.AtomicBoolean


class ParentRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RecyclerView(context, attrs, defStyleAttr) {

    private var mMaxDistance: Int = 0

    private val mHomeFlingHelper:HomeFlingHelper = HomeFlingHelper(context)

    private var lastY: Float = 0.toFloat()

    var canScrollVertically: AtomicBoolean


    var startFling: Boolean = false
    var totalDy: Int = 0
    private var velocityY: Int = 0
    init {
        mMaxDistance = mHomeFlingHelper.getVelocityByDistance((DpiUtil.getHeight(context) * 4).toDouble())

        canScrollVertically = AtomicBoolean(true)

        addOnScrollListener(object :OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    dispatchChildFling()
                }
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

    fun refreshLayoutManager() {
        val layoutManager = object :LinearLayoutManager(context){
            override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: State?): Int {
                try {
                    return super.scrollVerticallyBy(dy, recycler, state)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return 0
                }
            }

            override fun onLayoutChildren(recycler: Recycler?, state: State?) {
                try {
                    super.onLayoutChildren(recycler, state)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun canScrollVertically(): Boolean {
                val childRecyclerView:ChildRecyclerView? = findNestedScrollingChildView()
                return canScrollVertically.get() || childRecyclerView == null || childRecyclerView.isTop()
            }

            override fun addDisappearingView(child: View?) {
                try {
                    super.addDisappearingView(child)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun supportsPredictiveItemAnimations(): Boolean {
                return false
            }
        }

        layoutManager.orientation = LinearLayoutManager.VERTICAL

        this.layoutManager = layoutManager

    }

    fun dispatchChildFling() {
        if (isScrollEnd() && velocityY !== 0) {
            val splineFlingDistance = this.mHomeFlingHelper.getSplineFlingDistance(this.velocityY)
            if (splineFlingDistance > this.totalDy.toDouble()) {
                childFling(this.mHomeFlingHelper.getVelocityByDistance(splineFlingDistance - this.totalDy.toDouble()))
            }
        }
        this.totalDy = 0
        this.velocityY = 0
    }

    private fun childFling(velY: Int) {
        val childRecyclerView = findNestedScrollingChildView()
        Log.d("gaohui","called child fling $velY")
        if(childRecyclerView != null) {
            childRecyclerView.fling(0,velY)
        }
    }


    private fun isScrollEnd(): Boolean {
        return !canScrollVertically(1)
    }

    private fun findNestedScrollingChildView():ChildRecyclerView? {
        val adapter = adapter
        if((adapter is CustomAdapter).not()) {
            return null
        }
        val categoryView = (adapter as CustomAdapter).getCategoryView() ?: return null
        if(categoryView.mViewPager.currentItem in 0.. categoryView.fragmentList.size) {
            val fragment = categoryView.fragmentList[categoryView.mViewPager.currentItem]
            if(fragment is BlankFragment) {
               return fragment.getChildRecyclerView()
            }
            return null
        }

       return null
    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent?): Boolean {
        val i = 0
        if (motionEvent != null && motionEvent.action == MotionEvent.ACTION_DOWN) {
            velocityY = i
            stopScroll()
        }
        if (!(motionEvent == null || motionEvent.actionMasked == MotionEvent.ACTION_MOVE)) {
            lastY = 0.0f
        }
        return try {
            super.dispatchTouchEvent(motionEvent)
        } catch (e: Exception) {
            e.printStackTrace()
            i > 0
        }

    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val handled = false
        if (lastY == 0.0f) {
            lastY = motionEvent.y
        }
        if (isScrollEnd()) {
            val childRecyclerView =  findNestedScrollingChildView()
            if (childRecyclerView != null) {
                canScrollVertically.set(handled)
                childRecyclerView.scrollBy(0,(lastY - motionEvent.y).toInt())
            }
        }
        if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
            canScrollVertically.set(true)
        }
        lastY = motionEvent.y
        return try {
            super.onTouchEvent(motionEvent)
        } catch (e: Exception) {
            e.printStackTrace()
            handled
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

        val flag =  velocityY > 0.0f && isScrollEnd().not()
        val handle = !(velocityY > 0.0f || childRecyclerView == null || !childRecyclerView.isTop())
        if(!flag && !handle) {
            return false
        }
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


    override fun fling(velX: Int, velY: Int): Boolean {
        var tempVelY = velY
        val abs = Math.abs(tempVelY)
        if (mMaxDistance in 8889..(abs - 1)) {
            tempVelY = abs * mMaxDistance / tempVelY
        }
        val fling = super.fling(velX, tempVelY)
        if (!fling || tempVelY <= 0) {
            velocityY = 0
        } else {
            startFling = true
            velocityY = tempVelY
        }
        return fling
    }



}