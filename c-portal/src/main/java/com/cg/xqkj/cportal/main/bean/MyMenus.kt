package com.cg.xqkj.cportal.main.bean

import android.content.Context
import com.cg.xqkj.cportal.R

object MyMenus {

    /**
     * 工具菜单
     */
    fun getMyToolsMenus(context: Context):ArrayList<MenuBean>{
        val list:ArrayList<MenuBean> = ArrayList()
        list.add(MenuBean(R.mipmap.sw_my_icon_zhanghuchongzhi,context.getString(R.string.portal_my_account_recharge)))
        list.add(MenuBean(R.mipmap.sw_my_icon_maobichongzhi,context.getString(R.string.portal_my_cat_currency_recharge)))
        list.add(MenuBean(R.mipmap.sw_my_icon_maobichushou,context.getString(R.string.portal_my_cat_currency_sale)))
        list.add(MenuBean(R.mipmap.sw_my_icon_wodechushou,context.getString(R.string.portal_my_sale)))
        list.add(MenuBean(R.mipmap.sw_my_icon_maobiduihuan,context.getString(R.string.portal_my_cat_currency_convert)))
        list.add(MenuBean(R.mipmap.sw_my_icon_wodefabu,context.getString(R.string.portal_my_release)))
        list.add(MenuBean(R.mipmap.sw_my_icon_wodepinlun,context.getString(R.string.portal_my_comment)))
        list.add(MenuBean(R.mipmap.sw_my_icon_wodexiuwei,context.getString(R.string.portal_my_grade)))
        return list
    }

    /**
     * 功能菜单
     */
    fun getFunMenus(context: Context):ArrayList<MyFunBean>{
        val list:ArrayList<MyFunBean> = ArrayList()
        list.add(MyFunBean(MyMenuType.GIFT.ordinal,R.mipmap.sw_my_icon_lipin,context.getString(R.string.portal_my_gift_convert),getGiftMenus(context)))
        list.add(MyFunBean(MyMenuType.COLLECT.ordinal,R.mipmap.sw_my_icon_changpin,context.getString(R.string.portal_my_collect),getCollectMenus(context)))
        list.add(MyFunBean(MyMenuType.ACTION.ordinal,R.mipmap.sw_my_icon_zanmaobi,context.getString(R.string.portal_my_gather_cat_currency),getActMenus(context)))
        return list
    }

    /**
     * 攒猫币
     */
    private fun getActMenus(context: Context): ArrayList<MenuBean>? {
        val list:ArrayList<MenuBean> = ArrayList()
        list.add(MenuBean(R.mipmap.sw_my_icon_tuijian,context.getString(R.string.common_type_recommend)))
        list.add(MenuBean(R.mipmap.sw_my_icon_pinlun,context.getString(R.string.common_type_comment)))
        list.add(MenuBean(R.mipmap.sw_my_icon_yuedu,context.getString(R.string.common_type_read)))
        list.add(MenuBean(R.mipmap.sw_my_icon_fabu,context.getString(R.string.common_type_release)))
        list.add(MenuBean(R.mipmap.sw_my_icon_goumai,context.getString(R.string.common_type_buy)))
        return list
    }

    /**
     * 我的藏品
     */
    private fun getCollectMenus(context: Context): ArrayList<MenuBean>? {
        val list:ArrayList<MenuBean> = ArrayList()
        list.add(MenuBean(R.mipmap.sw_my_icon_me_lipin,context.getString(R.string.common_type_gift)))
        list.add(MenuBean(R.mipmap.sw_my_icon_me_yuyin,context.getString(R.string.common_type_audio)))
        list.add(MenuBean(R.mipmap.sw_my_icon_me_tuwen,context.getString(R.string.common_type_image_text)))
        list.add(MenuBean(R.mipmap.sw_my_icon_me_shipin,context.getString(R.string.common_type_video)))
        list.add(MenuBean(R.mipmap.sw_my_icon_me_xiaoshuo,context.getString(R.string.common_type_fiction)))
        return list
    }

    /**
     * 礼品兑换
     */
    private fun getGiftMenus(context: Context): ArrayList<MenuBean>? {
        val list:ArrayList<MenuBean> = ArrayList()
        list.add(MenuBean(R.mipmap.sw_my_gift_djyp,context.getString(R.string.common_type_djyp)))
        list.add(MenuBean(R.mipmap.sw_my_gift_smcp,context.getString(R.string.common_type_smcp)))
        list.add(MenuBean(R.mipmap.sw_my_gift_jjlp,context.getString(R.string.common_type_jjlp)))
        return list
    }
}