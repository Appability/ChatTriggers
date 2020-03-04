package com.chattriggers.ctjs.launch.plugin

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject

fun injectCrashReport() = inject {
    className = "net/minecraft/crash/CrashReport"
    methodName = "populateEnvironment"
    methodDesc = "()V"
    at = At(InjectionPoint.HEAD)

    fieldMaps = mapOf("theReportCategory" to "field_85061_c")
    methodMaps = mapOf("func_71504_g" to "populateEnvironment")

    insnList {
        invokeKOBjectFunction("com/chattriggers/ctjs/launch/AsmUtils", "addCrashSectionCallable", "(L$CRASH_REPORT_CATEGORY;)V") {
            getLocalField(className, "theReportCategory", "L$CRASH_REPORT_CATEGORY;")
        }
    }
}
