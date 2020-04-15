package cn.com.cg.ccommon.utils
import android.app.Application
import android.content.res.AssetManager
import android.text.TextUtils
import androidx.annotation.WorkerThread
import java.io.*
import java.lang.Exception


/**
 * 从assets复制内容到sd卡的工具类
 * 1、增加复制成功的标识，便于判断复制中途失败的场景
 * 2、兼容复制内容嵌套文件夹的场景
 */
object AssetsUtils {


    //复制成功的标记值，故意写一个无意义的单词，避免重复
    val fileName = "SiWenMao"

    /**
     * 从assets目录复制到素材中心在sd卡的目录下
     *
     * @param inputPath
     * @return 是否成功处理
     */
    @WorkerThread
    fun copyFromAssetsToMaterialPath(
        application: Application,
        inputPath: String,
        outputPath: String,
        maskSuccess: Boolean
    ): Boolean {
        try {
            Thread.currentThread()
            var inputPath = inputPath
            if (TextUtils.isEmpty(inputPath)) {
                return false
            }

            if (inputPath.endsWith(File.separator)) {
                inputPath = inputPath.substring(0, inputPath.length - 1)
            }

            //从assets复制
            val assetManager = application.assets

            val list: Array<String>?
            try {
                list = assetManager.list(inputPath)
            } catch (e: IOException) {
                return true
            }

            if (list == null || list.isEmpty()) {
                if (maskSuccess) {
                    val file = File(outputPath, fileName)
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
                //如果有assets文件不存在，也当做成功，因为默认无效果也会当做素材来处理
                return true
            }

            for (fileName in list) {
                copyAssetsListFile(assetManager, inputPath, fileName, outputPath)
            }

            if (maskSuccess) {
                val file = File(outputPath, fileName)
                file.createNewFile()
            }

            return true
        }catch (e:Exception){
            e.printStackTrace()
        }
       return false
    }


        private fun copyAssetsListFile(assetManager: AssetManager, input: String, fileName: String, output: String) {
            try {
                val innerList = assetManager.list(input + File.separator + fileName)
                if (innerList.isNullOrEmpty()) {
                    copySingleFile(assetManager, input, fileName, output)
                } else {
                    for (innerFile in innerList) {
                        copyAssetsListFile(assetManager, input + File.separator + fileName, innerFile, output + File.separator + fileName)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        private fun copySingleFile(assetManager: AssetManager, input: String, fileName: String, output: String): Boolean {
            val outFile = File(output, fileName)
            if (!outFile.parentFile.exists()) {
                outFile.parentFile.mkdirs()
            }

            var inputStream: InputStream? = null
            var out: OutputStream? = null

            try {
                inputStream = assetManager.open(input + File.separator + fileName)
                out = FileOutputStream(outFile)

                val buffer = ByteArray(1024)
                var read: Int = inputStream!!.read(buffer)
                while (read != -1) {
                    out.write(buffer, 0, read)
                    read = inputStream!!.read(buffer)
                }
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {

                    }

                }
                if (out != null) {
                    try {
                        out.flush()
                        out.close()
                    } catch (e: IOException) {

                    }

                }
            }

            return true
        }

}