package cn.com.cg.ccommon.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import cn.com.cg.router.annotation.CMethod

/**
 *  author : chenguo
 *  date : 2019/12/12 21:01
 *  description : { 请添加该类的描述 }
 */
class AnimUtils {

    @CMethod(path = "/AnimUtils/slideToUp")
    public fun slideToUp(vararg params: Any) {
        val view = params[0] as View
        val slide = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        slide.duration = 400
        slide.fillAfter = true
        slide.isFillEnabled = true
        view.startAnimation(slide)
        slide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    @CMethod(path = "/AnimUtils/upToSlide")
    public fun upToSlide(vararg params: Any) {
        val view = params[0] as View
        val slide = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        slide.duration = 400
        slide.fillAfter = true
        slide.isFillEnabled = true
        view.startAnimation(slide)
        slide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    @CMethod(path = "/AnimUtils/rightToLeft")
    public fun rightToLeft(vararg params: Any) {
        val view = params[0] as View
        val slide = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        slide.duration = 400
        slide.fillAfter = true
        slide.isFillEnabled = true
        view.startAnimation(slide)
        slide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    @CMethod(path = "/AnimUtils/leftToRight")
    public fun leftToRight(vararg params: Any) {
        val view = params[0] as View
        val slide = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, -1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        slide.duration = 400
        slide.fillAfter = true
        slide.isFillEnabled = true
        view.startAnimation(slide)
        slide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }
}