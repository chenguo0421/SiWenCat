package cn.com.cg.ccommon.widget.bounce.intf

interface BounceListener {
    /**
     * 顶部弹跳
     *
     * @param bouncingJelly 弹跳系数
     */
    fun onBounceTop(bouncingJelly:Float)

    /**
     * 底部弹跳
     *
     * @param bouncingJelly 弹跳系数
     */
    fun onBounceBottom(bouncingJelly:Float)
}
