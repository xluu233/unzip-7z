import java.io.File
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//7z软件目录
const val `7zPath` = "\"C:\\Program Files\\7-Zip\\7z.exe\""

//解压目录
const val destDir = "E:\\夸克解压"

private val fileMap = ConcurrentHashMap<String,String>()

private val threadPool by lazy {
    Executors.newFixedThreadPool(3)
}

fun main(args: Array<String>) {
    println("开始遍历文件")

    val file = File("D:\\夸克下载")
    file.listFiles()?.forEach {
        fileMap.put(it.absolutePath,it.name)
        println("path:${it.absoluteFile}  key:${it.name}")
    }

    fileMap.forEach {
        threadPool.execute {
            startUnzip(it.key,it.value)
        }
    }

}

fun startUnzip(srcFilePath: String, password: String) {

    //拼接命令行
    val cmd = "$`7zPath` x $srcFilePath -o$destDir -p${password}"

    println("开始任务：$cmd")

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
    if (bool){
        File(srcFilePath).delete()
        println("解压成功，已删除:$srcFilePath")
    }else{
        println("解压失败:$srcFilePath")
    }
}
