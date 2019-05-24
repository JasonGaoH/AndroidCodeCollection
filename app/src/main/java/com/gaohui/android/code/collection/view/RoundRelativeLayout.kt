package com.gaohui.android.code.collection.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.RelativeLayout

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class RoundRelativeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val mPath = Path()
    private val paint = Paint()

    private val sMetrics = Resources.getSystem().displayMetrics

    val round = dp2px(8f)


    init {
        paint.isAntiAlias = true

        val bg = GradientDrawable()
        bg.cornerRadius = round.toFloat()
        bg.setColor(Color.RED)
        background = bg
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mPath.reset()

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