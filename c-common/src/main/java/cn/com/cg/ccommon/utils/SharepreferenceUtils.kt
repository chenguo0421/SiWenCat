package cn.com.cg.ccommon.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.SharedPreferencesCompat

/**
 * Discription  { 首选项工具 }
 * author  chenguo7
 * Date  2019/11/25 16:28
 */
class SharepreferenceUtils {
    /**
     * 获取首选项
     *
     * @param context
     * @return SharedPreferences sp
     */
    fun getSP(context: Context): SharedPreferences {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    /**
     * 获取首选项中String类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(String)
     */
    fun getString(context: Context, key: String, defaultValue: String): String? {
        val sp = getSP(context)
        return sp.getString(key, defaultValue)
    }

    /**
     * 获取首选项中String类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(String)
     */
    fun getString(context: Context, key: String): String? {
        val sp = getSP(context)
        return sp.getString(key, "")
    }

    /**
     * 获取首选项中boolean类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(boolean)
     */
    fun getBoolean(context: Context, key: String): Boolean {
        val sp = getSP(context)
        return sp.getBoolean(key, false)
    }

    /**
     * 获取首选项中boolean类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(boolean)
     */
    fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        val sp = getSP(context)
        return sp.getBoolean(key, defaultValue)
    }

    /**
     * 获取首选项中int类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(int)
     */
    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        val sp = getSP(context)
        return sp.getInt(key, defaultValue)
    }

    /**
     * 获取首选项中int类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(int)
     */
    fun getInt(context: Context, key: String): Int {
        val sp = getSP(context)
        return sp.getInt(key, 0)
    }

    /**
     * 获取首选项中long类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(long)
     */
    fun getLong(context: Context, key: String, defaultValue: Int): Long {
        val sp = getSP(context)
        return sp.getLong(key, defaultValue.toLong())
    }

    /**
     * 获取首选项中long类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(long)
     */
    fun getLong(context: Context, key: String): Long {
        val sp = getSP(context)
        return sp.getLong(key, 0)
    }

    /**
     * 获取首选项中float类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(float)
     */
    fun getFloat(context: Context, key: String, defaultValue: Int): Float {
        val sp = getSP(context)
        return sp.getFloat(key, defaultValue.toFloat())
    }

    /**
     * 获取首选项中float类型值，对应键值为key
     *
     * @param context
     * @param key
     * @return key对应的值(float)
     */
    fun getFloat(context: Context, key: String): Float {
        val sp = getSP(context)
        return sp.getFloat(key, 0f)
    }

    /**
     * 向首选项中编辑数据(键值对)(值可以是String boolean int)
     *
     * @param context
     * @param key
     * @param value
     */
    fun put(context: Context, key: String, value: Any) {
        val sp = getSP(context)
        val editor = sp.edit()

        if (value is String) {
            editor.putString(key, value)
        } else if (value is Boolean) {
            editor.putBoolean(key, value)
        } else if (value is Int) {
            editor.putInt(key, value)
        } else if (value is Long) {
            editor.putLong(key, value)
        } else if (value is Float) {
            editor.putFloat(key, value)
        }

        editor.commit()
    }

    /**
     * 移除key对应值
     *
     * @param context
     * @param key
     */
    fun remove(context: Context, key: String) {
        val sp = getSP(context)
        val editor = sp.edit().remove(key)
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor)
    }

    /**
     * 清除所有内容
     *
     * @param context
     */
    fun clear(context: Context) {
        val sp = getSP(context)
        val editor = sp.edit().clear()
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor)
    }
}