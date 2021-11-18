package com.example.employeedirection.common.utils

import java.io.File

object FileUtil {
    fun readFileAsTextUsingInputStream(fileName: String)
            = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

//    val classLoader = obj.javaClass.classLoader
//    val resource = classLoader.getResource(fileName)
//    return File(resource.path)
}