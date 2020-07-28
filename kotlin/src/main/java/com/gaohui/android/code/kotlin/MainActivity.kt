package com.gaohui.android.code.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var start = System.currentTimeMillis()
        Observable.range(0, 1_000_000)
            .map { it + 1 }
            .map { it + 1 }
            .map { it + 1 }
            .map { it + 1 }
            .map { it + 1 }
            .blockingLast()
        Log.d("gaohui","RxJava range " + (System.currentTimeMillis() - start))

        start = System.currentTimeMillis()
        (1..1_000_000)
//            .asSequence()
            .map { it + 1 }
            .map { it + 1 }
            .map { it + 1 }
            .map { it + 1 }
            .map { it + 1 }
            .last()

        Log.d("gaohui","Kotlin range " + (System.currentTimeMillis() - start))
    }
}
