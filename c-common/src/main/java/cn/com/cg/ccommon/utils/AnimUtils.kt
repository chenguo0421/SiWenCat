package cn.com.cg.ccommon.utils

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import cn.com.cg.router.annotation.CMethod

/**
 *  author : chenguo
 *  date : 2019/12/12 21:01
 *  description : { 请添加该类的描述 }
 */
class AnimUtils {

    public fun slideToUp(view: View) {
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

    private fun upToSlide(view: View) {
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

    private fun rightToLeft(view: View) {
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

    private fun leftToRight(view: View) {
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

    private fun alphaView(view:View,from:Float,to:Float,duration:Long){
        val alphaAnim = AlphaAnimation(from,to)
        alphaAnim.duration = duration
        view.startAnimation(alphaAnim)
        alphaAnim.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
        })
    }

    @CMethod(path = "/AnimUtils/slideToUp")
    public fun slideToUp(vararg params: Any) {
        val view = params[0] as View
        slideToUp(view)
    }

    @CMethod(path = "/AnimUtils/upToSlide")
    public fun upToSlide(vararg params: Any) {
        val view = params[0] as View
        upToSlide(view)
    }

    @CMethod(path = "/AnimUtils/rightToLeft")
    public fun rightToLeft(vararg params: Any) {
        val view = params[0] as View
        rightToLeft(view)
    }

    @CMethod(path = "/AnimUtils/leftToRight")
    public fun leftToRight(vararg params: Any) {
        val view = params[0] as View
        leftToRight(view)
    }

    @CMethod(path = "/AnimUtils/alphaView")
    public fun alphaView(vararg  params:Any){
        val view = params[0] as View
        val fronAlpha = params[1] as Float
        val toAlpha = params[2] as Float
        val duration = params[3] as Long
        alphaView(view,fronAlpha,toAlpha,duration)
    }
}