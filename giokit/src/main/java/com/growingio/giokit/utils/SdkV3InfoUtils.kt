package com.growingio.giokit.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import com.growingio.giokit.hook.GioPluginConfig
import com.growingio.giokit.launch.sdkinfo.SdkInfo
import org.json.JSONException
import org.json.JSONObject

/**
 * <p>
 *
 * @author cpacm 2021/8/26
 */
object SdkV3InfoUtils {
    //获取 sdk 信息
    fun getSdkInfo(): List<SdkInfo> {
        val list = arrayListOf<SdkInfo>()
        list.add(SdkInfo("GrowingIO SDK信息", isHeader = true))
        val hasDepend = hasClass("com.growingio.android.sdk.track.providers.ConfigurationProvider")
        if (hasDepend) {
            val (_, sdkVersion, _) = GioPluginConfig.analyseDepend()
            list.tryAdd { SdkInfo("SDK版本", sdkVersion) }
            list.tryAdd { SdkInfo("项目ID","我是项目id") }
            list.tryAdd { SdkInfo("加密","RSA + AES") }
        } else {
            list.add(SdkInfo("SDK", "SDK未集成"))
        }

        return list
    }


    private fun hasClass(className: String): Boolean {
        try {
            Class.forName(className)
            return true
        } catch (e: ClassNotFoundException) {
            return false
        }
    }

    fun getAppInfo(context: Context): List<SdkInfo> {
        val list = arrayListOf<SdkInfo>()
        val pi = getPackageInfo(context)
        if (pi != null) {
            list.add(SdkInfo("App信息", isHeader = true))
            list.add(SdkInfo("包名", pi.packageName))
            list.add(SdkInfo("应用版本名", pi.versionName))
            list.add(SdkInfo("应用版本号", pi.versionCode.toString()))

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                list.add(SdkInfo("最低系统版本号", context.applicationInfo.minSdkVersion.toString()))
            }
            list.add(SdkInfo("目标系统版本号", context.applicationInfo.targetSdkVersion.toString()))
        }
        return list
    }

    private fun getPackageInfo(context: Context): PackageInfo? {
        var pi: PackageInfo? = null
        try {
            val pm = context.packageManager
            pi = pm.getPackageInfo(
                context.packageName,
                PackageManager.GET_CONFIGURATIONS
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return pi
    }

    fun getMobileInfo(context: Context): List<SdkInfo> {
        val list = arrayListOf<SdkInfo>()
        list.add(SdkInfo("设备信息", isHeader = true))
        list.add(SdkInfo("手机型号", Build.MANUFACTURER + " " + Build.MODEL))
        list.add(SdkInfo("系统版本", Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"))
        list.tryAdd { SdkInfo("SD卡剩余空间", DeviceUtils.getSDCardSpace(context)) }
        list.tryAdd { SdkInfo("系统剩余空间", DeviceUtils.getRomSpace(context)) }
        list.tryAdd {
            SdkInfo(
                "分辨率",
                "${DeviceUtils.getWidthPixels(context)}x${
                    DeviceUtils.getRealHeightPixels(
                        context
                    )
                }"
            )
        }
        if (context is Activity) {
            list.tryAdd { SdkInfo("屏幕尺寸", DeviceUtils.getScreenInch(context).toString()) }
        }
        list.tryAdd { SdkInfo("ROOT", DeviceUtils.isRoot(context).toString()) }
        list.tryAdd { SdkInfo("DENSITY", Resources.getSystem().displayMetrics.density.toString()) }
        list.tryAdd { SdkInfo("IP", DeviceUtils.getIPAddress(true)) }

        return list
    }



    fun getEventAlphaBet(eventType: String): String {
        return "getEventAlphaBet"
    }

    fun getEventDesc(eventType: String, data: String): String {
        try {
            val jsonObj = JSONObject(data)
            // custom name
            val name = jsonObj.optString("eventName")
            if (name.isNotEmpty()) return name

            // page
            val p = jsonObj.optString("path")
            if (p.isNotEmpty()) return p


            return jsonObj.optString("appName")

        } catch (e: JSONException) {
        }
        return data
    }
}