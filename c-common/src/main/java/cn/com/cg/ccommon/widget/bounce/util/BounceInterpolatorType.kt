package cn.com.cg.ccommon.widget.bounce.util

import android.animation.TimeInterpolator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator


class BounceInterpolatorType {
    companion object{
        const val OVERSHOOT_INTERPOLATOR = 1
        const val BOUNCE_INTERPOLATOR = 2
        const val LINEAR_INTERPOLATOR = 3
        const val ACCELERATE_DECELERATE_INTERPOLATOR = 4

        /**
         * get Bouncing type
         *
         * @return
         */
        fun getTimeInterpolator(type: Int): TimeInterpolator? {
            var mTimeInterpolator: TimeInterpolator? = null
            when (type) {
                OVERSHOOT_INTERPOLATOR -> mTimeInterpolator = OvershootInterpolator()
                BOUNCE_INTERPOLATOR -> mTimeInterpolator = BounceInterpolator()
                LINEAR_INTERPOLATOR -> mTimeInterpolator = LinearInterpolator()
                ACCELERATE_DECELERATE_INTERPOLATOR -> mTimeInterpolator =
                    AccelerateDecelerateInterpolator()
            }
            return mTimeInterpolator
        }
    }



}