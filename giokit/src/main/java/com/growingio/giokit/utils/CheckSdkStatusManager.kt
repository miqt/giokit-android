package com.growingio.giokit.utils


import com.growingio.giokit.hook.GioPluginConfig
import com.growingio.giokit.hook.GioTrackInfo
import com.growingio.giokit.hover.check.CheckItem

/**
 * <p>
 *
 * @author cpacm 2022/8/10
 */
class CheckSdkStatusManager private constructor(val checkSdkStatus: CheckSdkStatusInterface) :
    CheckSdkStatusInterface by checkSdkStatus {

    fun getEventAlphaBet(eventType: String): String {
        return SdkV3InfoUtils.getEventAlphaBet(eventType)
    }

    fun getEventDesc(eventType: String, data: String): String {
        return SdkV3InfoUtils.getEventDesc(eventType, data)

    }

    fun eventFilterProxy() {

    }

    fun getSdkDepend(index: Int): CheckItem {
        val (title, content, isError) = GioPluginConfig.analyseDepend()
        return CheckItem(index, "正在获取SDK版本", title, content, isError)
    }

    fun hasSdkPlugin(index: Int): CheckItem {
        val (content, isError) = GioPluginConfig.hasSdkPlugin()
        return CheckItem(index, "正在获取SDK插件", "SDK插件", content, isError)
    }

    fun getTrackCount(index: Int): CheckItem {
        return CheckItem(
            index,
            "正在获取手动埋点数目",
            "手动埋点",
            "共有 ${GioTrackInfo.trackList.size} 处（不包括自动埋点）",
            GioTrackInfo.trackList.size <= 0
        )
    }

    companion object {
        private var instance: CheckSdkStatusManager? = null

        @JvmStatic
        fun getInstance(): CheckSdkStatusManager =
            CheckSdkStatusManager(object : CheckSdkStatusInterface {
                override fun getProjectStatus(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);

                }

                override fun getURLScheme(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);
                }

                override fun getDataSourceID(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);

                }

                override fun getProjectID(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);

                }

                override fun getDataServerHost(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);

                }

                override fun getDataCollectionEnable(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);

                }

                override fun getSdkDebug(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);

                }

                override fun getSdkModules(index: Int): CheckItem {
                    return CheckItem(1, "loading", "title", "content", false);

                }

            });

        fun checkSdkInit(): Boolean {
            // TODO: 初始化状态
            return true
        }

        fun hasClass(className: String): Boolean {
            return try {
                Class.forName(className)
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

        fun hasMethodNoParam(obj: Any, className: String, method: String): Any? {
            return try {
                val clazz = Class.forName(className)
                val m = clazz.getDeclaredMethod(method)
                return m.invoke(obj)
            } catch (e: ClassNotFoundException) {
                null
            } catch (e: NoSuchMethodException) {
                null
            } catch (e: SecurityException) {
                null
            } catch (e: Exception) {
                null
            }
        }

        fun getClassField(obj: Any, className: String, field: String): Any? {
            return try {
                val clazz = Class.forName(className)
                val f = clazz.getDeclaredField(field)
                f.isAccessible = true
                return f.get(obj)
            } catch (e: ClassNotFoundException) {
                null
            } catch (e: NoSuchFieldException) {
                null
            } catch (e: SecurityException) {
                null
            } catch (e: Exception) {
                null
            }
        }
    }
}

interface CheckSdkStatusInterface {
    fun getProjectStatus(index: Int): CheckItem
    fun getURLScheme(index: Int): CheckItem
    fun getDataSourceID(index: Int): CheckItem
    fun getProjectID(index: Int): CheckItem
    fun getDataServerHost(index: Int): CheckItem
    fun getDataCollectionEnable(index: Int): CheckItem
    fun getSdkDebug(index: Int): CheckItem
    fun getSdkModules(index:Int):CheckItem
}