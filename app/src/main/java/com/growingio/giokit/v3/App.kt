package com.growingio.giokit.v3

import android.app.Application
import com.dians.stc.ConfigBuilder
import com.dians.stc.StcDians
import com.growingio.giokit.GioKit
import com.growingio.giokit.hook.GioDatabase

/**
 * <p>
 *
 * @author cpacm 2021/8/12
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initGioSdk()

        GioKit.with(this).build()
    }



    fun initGioSdk() {
        //请务必在Application onCreate中初始化
        //请务必在Application onCreate中初始化
        StcDians.init(
            applicationContext,
            ConfigBuilder.withAppKey("testkey")
                .setAutoHeatMap(false)
                .setChannel("应用宝")
                .setAutoTrackClick(true)
                .setAutoTrackPageView(true)
                .setAutoTrackFragmentPageView(false)
                .setDebugMode(true) //上线时改为false
                .builder(), null
        ) // <--传入ids

        // 同意true正常采集数据，不同意false停止采集工作。
        // 同意true正常采集数据，不同意false停止采集工作。
        StcDians.setDataCollectEnable(applicationContext, true)
        StcDians.addEventListener {
            GioDatabase.insertSaasEvent(it)
        }
    }
}