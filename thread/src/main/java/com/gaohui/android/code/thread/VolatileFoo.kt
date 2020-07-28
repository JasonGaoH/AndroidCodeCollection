package com.gaohui.android.code.thread

import android.util.Log

object VolatileFoo {
    //init_value的最大值
    const val MAX = 5

    //init_value的初始值
//    @Volatile
    var init_value = 0

    //	 static int[] intArr = new int[]{0,0};
//    @JvmStatic
    fun main() {
        //启动一个Reader线程，当发现local_value和init_value不同时，
        //则输出init_value被修改的信息
        Thread(Runnable {

            //			int localValue = init_value;
            var localValue = init_value
            while (localValue < MAX) {
                if (init_value != localValue) {
                    Log.d("gaohui","this init_value is updated to $init_value")
                    //对local_value重新赋值
                    localValue = init_value
                }
            }
        }, "Readder").start()
        Thread(Runnable {
            var localValue = init_value
            while (localValue < MAX) {
                Log.d("gaohui","this init_value will be changed to " + ++localValue)
                //对local_value重新赋值
                init_value = localValue
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }, "Updater").start()
    }
}