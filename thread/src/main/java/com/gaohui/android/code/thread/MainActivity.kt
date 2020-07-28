package com.gaohui.android.code.thread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

//    @Volatile
//    var strArrayList = Collections.synchronizedList(ArrayList<Any>())
    var strArrayList = ArrayList<String>()

    var lock = Any()

    //init_value的最大值
     val MAX = 5
    //init_value的初始值
//    @Volatile
    var init_value = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        strArrayList.add("1")
//        strArrayList.add("2")
//        strArrayList.add("3")
//
//        (0..1000).forEach {
//            val myThread = MyThread(strArrayList,lock)
//            myThread.start()
//        }

//        Thread {
//            var localValue = init_value
//            while (localValue < MAX) {
//                if(init_value != localValue) {
//                    Log.d("gaohui","this init_value is updated to $init_value")
//                    localValue = init_value
//                }
//            }
//        }.start()
//
//
//        Thread {
//            var localValue = init_value
//            while (localValue < MAX) {
//                Log.d("gaohui","this init_value will be changed to ${ ++localValue}")
//                init_value = localValue
//               try {
//                   Thread.sleep(200)
//               } catch (e:Exception) {
//
//               }
//            }
//        }.start()


        VolatileFoo.main()
    }




//    public static void main(String[] args) {
//        //启动一个Reader线程，当发现local_value和init_value不同时，
//        //则输出init_value被修改的信息
//        new Thread(() -> {
//            int localValue = init_value;
//            while(localValue < MAX) {
//                if(init_value != localValue) {
//                    System.out.println("this init_value is updated to " + init_value);
//                    //对local_value重新赋值
//                    localValue = init_value;
//                }
//            }
//        },"Readder").start();
//
//        new Thread(() -> {
//            int localValue = init_value;
//            while(localValue < MAX) {
//                System.out.println("this init_value will be changed to " + ++localValue);
//                //对local_value重新赋值
//                init_value = localValue;
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        },"Updater").start();
//    }


}

class MyThread(val arrayList: MutableList<String>, val lock: Any) : Thread() {

    override fun run() {
        super.run()
//        arrayList.remove("1")
        arrayList.add("1")

        Log.d("gaohui","arrayList size ${arrayList.size}")
    }
}
