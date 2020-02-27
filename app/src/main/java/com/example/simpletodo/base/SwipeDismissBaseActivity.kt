package com.example.simpletodo.base

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

abstract class SwipeDismissBaseActivity : AppCompatActivity() {
    private var gestureDetector: GestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestureDetector = GestureDetector(SwipeDetector())
    }

    private inner class SwipeDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (abs(e1.y - e2.y) > SWIPE_MAX_OFF_PATH) return false

            if (e2.x - e1.x > SWIPE_MIN_DISTANCE && abs(
                    velocityX
                ) > SWIPE_THRESHOLD_VELOCITY
            ) {
                finish()
                return true
            }
            return false
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        gestureDetector?.let {
            if (it.onTouchEvent(ev))
                return true
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector?.onTouchEvent(event) ?: super.onTouchEvent(event)
    }

    companion object {
        private const val SWIPE_MIN_DISTANCE = 120
        private const val SWIPE_MAX_OFF_PATH = 250
        private const val SWIPE_THRESHOLD_VELOCITY = 200
    }
}