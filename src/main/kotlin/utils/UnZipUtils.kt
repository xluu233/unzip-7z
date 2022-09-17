package utils

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.FileHeader
import kotlin.Throws
import java.io.IOException
import kotlin.jvm.JvmStatic
import utils.UnZipUtils
import java.io.File
import java.util.ArrayList

/**
 * @Auther: Jax
 * @Date: 2018/6/28 10:03
 * @Description:zip文件解压缩工具类
 */
class UnZipUtils {

    companion object {
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val z = UnZipUtils()
            val source = "D:\\夸克下载\\@八仙过海A各显W神通W\\八仙过海.part1.rar"
            val dest = "D:\\夸克下载\\@八仙过海A各显W神通W\\test"
            val password = "@八仙过海A各显W神通W"
            z.unZip(source, dest, password)
        }
    }

    /**
     * @param source   原始文件路径
     * @param dest     解压路径
     * @param password 解压文件密码(可以为空)
     */
    fun unZip(source: String?, dest: String?, password: String) {
        try {
            val zipFile = File(source)

            println(zipFile.absoluteFile)

            // 首先创建ZipFile指向磁盘上的.zip文件
            val zFile = ZipFile(zipFile)

            // 解压目录
            val destDir = File(dest)
            if (!destDir.exists()) {
                // 目标目录不存在时，创建该文件夹
                destDir.mkdirs()
            }

            if (zFile.isEncrypted) {
                // 设置密码
                zFile.setPassword(password.toCharArray())
            }
            // 将文件抽出到解压目录(解压)
            zFile.extractAll(dest)
            val headerList = zFile.fileHeaders
            val extractedFileList: MutableList<File> = ArrayList()
            for (fileHeader in headerList) {
                if (!fileHeader.isDirectory) {
                    extractedFileList.add(File(destDir, fileHeader.fileName))
                }
            }
            extractedFileList.forEach {
                println("文件解压成功!:${it.absolutePath}")
            }
        } catch (e: ZipException) {
            e.printStackTrace()
        }
    }


}