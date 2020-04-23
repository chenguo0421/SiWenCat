package cn.com.cg.ccommon.utils
import android.content.Context
import android.util.Log
import cn.com.cg.ccommon.crash.CrashHandler.Companion.TAG
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * 从assets复制内容到sd卡的工具类
 * 1、增加复制成功的标识，便于判断复制中途失败的场景
 * 2、兼容复制内容嵌套文件夹的场景
 */
object AssetsUtils {

    fun getJsonArrayString(context: Context,fileName:String): String? {
        var inputStream: InputStream? = null
        var bos: ByteArrayOutputStream? = null
        var jsonFileName = fileName
        try {
            if (jsonFileName.startsWith("/")) {
                jsonFileName = jsonFileName.substring(1)
            }
            inputStream = context.assets.open(jsonFileName)
            bos = ByteArrayOutputStream()
            val bytes = ByteArray(4 * 1024)
            var len = 0
            while (inputStream.read(bytes).also { len = it } != -1) {
                bos.write(bytes, 0, len)
            }
            var jsonStr = String(bos.toByteArray())
            return JSONObject(jsonStr).getJSONArray("data").toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
                bos?.close()
            } catch (e: IOException) {
                Log.e(TAG, "getStates", e)
            }
        }
        return null
    }

    fun getJsonObjectString(context: Context,fileName:String): String? {
        var inputStream: InputStream? = null
        var bos: ByteArrayOutputStream? = null
        var jsonFileName = fileName
        try {
            if (jsonFileName.startsWith("/")) {
                jsonFileName = jsonFileName.substring(1)
            }
            inputStream = context.assets.open(jsonFileName)
            bos = ByteArrayOutputStream()
            val bytes = ByteArray(4 * 1024)
            var len = 0
            while (inputStream.read(bytes).also { len = it } != -1) {
                bos.write(bytes, 0, len)
            }
            var jsonStr = String(bos.toByteArray())
            return JSONObject(jsonStr).getJSONObject("data").toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
                bos?.close()
            } catch (e: IOException) {
                Log.e(TAG, "getStates", e)
            }
        }
        return null
    }

}