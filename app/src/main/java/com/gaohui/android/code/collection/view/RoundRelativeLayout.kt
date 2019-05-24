package com.gaohui.android.code.collection.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.RelativeLayout

class RoundRelativeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val mPath = Path()
    private val paint = Paint()

    private val sMetrics = Resources.getSystem().displayMetrics


    init {
        paint.isAntiAlias = true
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mPath.reset()
        val round = dp2px(8f)
        mPath.addRoundRect(RectF(paddingLeft.toFloat(), paddingTop.toFloat(), w - paddingRight.toFloat(), h - paddingBottom.toFloat()), round.toFloat(), round.toFloat(), Path.Direction.CW)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.clipPath(mPath)
        super.dispatchDraw(canvas)
    }


    private fun dp2px(dipValue: Float): Int {
        val scale = sMetrics?.density ?: 1f
        return (dipValue * scale + 0.5f).toInt()
    }
}