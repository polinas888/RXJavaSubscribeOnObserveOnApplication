package com.example.rxjavasubscribeonobserveonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.fromCallable {
            Log.i("CurrentThread", "1 ${Thread.currentThread()}")
        }
            .map {
                Log.i("CurrentThread", "2 ${Thread.currentThread()}")
            }
            .observeOn(getNamedScheduler("Thread1")) // Thread 3
            .map {
                Log.i("CurrentThread", "3 ${Thread.currentThread()}")
            }
            .subscribeOn(getNamedScheduler("Thread2")) // Thread 2
            .subscribeOn(getNamedScheduler("Thread3"))
            .map {
                Log.i("CurrentThread", "4 ${Thread.currentThread()}")
            }
            .observeOn(getNamedScheduler("Thread4")) //4
            .map {
                Log.i("CurrentThread", "5 ${Thread.currentThread()}")
            }
            .subscribe {
                Log.i("CurrentThread", "6 ${Thread.currentThread()}")
            }
    }

    private fun getNamedScheduler(name: String): Scheduler =  RxJavaPlugins.createNewThreadScheduler { Thread(it, name) }
}
