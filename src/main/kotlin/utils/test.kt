package utils

import java.lang.Exception

fun main() {

    val `7zPath` = "\"C:\\Program Files\\7-Zip\\7z.exe\""

    //压缩文件路径
    val compressFile = "D:\\夸克下载\\@八仙过海A各显W神通W\\八仙过海.part1.rar"
    //解压目录
    val destDir = "D:\\test"
    //密码
    val pwd = "@八仙过海A各显W神通W"

    val cmd = "$`7zPath` x $compressFile -o$destDir -p${pwd}"

    println(cmd)

    var bool = false
    try {
        val proc = Runtime.getRuntime().exec(cmd)
        if (proc.waitFor() != 0) {
            if (proc.exitValue() == 0) {
                bool = false
            }
        } else {
            bool = true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    println("解压" + if (bool) "成功" else "失败")
}