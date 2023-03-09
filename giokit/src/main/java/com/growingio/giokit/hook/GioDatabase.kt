package com.growingio.giokit.hook

import android.text.TextUtils
import com.growingio.giokit.GioKitImpl
import com.growingio.giokit.launch.db.GioKitDbManager
import com.growingio.giokit.launch.db.GioKitEventBean
import org.json.JSONException
import org.json.JSONObject

/**
 * <p>
 *
 * @author cpacm 2021/8/31
 */
object GioDatabase {

    var index = 0L
    /**
     * 插入2.0数据
     */
    @JvmStatic
    fun insertSaasEvent(data: String) {
        val gioEvent = GioKitEventBean()
        gioEvent.status = GioKitEventBean.STATUS_SENDED
        try {
            val jsonObject = JSONObject(data)
            gioEvent.data = jsonObject.toString(2)
            gioEvent.time = jsonObject.optLong("itime")
            gioEvent.gsid = index++
            gioEvent.type =
                if (jsonObject.has("event")) jsonObject.optString("event") else jsonObject.optString(
                    "ltype"
                )
            val url = jsonObject.getJSONObject("lprops").optString("\$url")
            if (!TextUtils.isEmpty(url)) {
                gioEvent.path = url
            }
            gioEvent.extra = "{a=1}"
        } catch (e: JSONException) {
        }
        GioKitDbManager.instance.insertEvent(gioEvent)
    }



    @JvmStatic
    fun removeSaasEvents(extra: String, lastId: String) {
        GioKitDbManager.instance.removeSaasEvents(extra, lastId)
    }

    /************************* sdk 3.0 *************************/
    /**
     * 将7天前的事件置为过期
     */
    @JvmStatic
    fun outdatedEvents() {
        if (GioKitImpl.inited) {
            GioKitDbManager.instance.outdatedEvents()
        }
    }



    @JvmStatic
    fun deleteEvent(id: Long) {
        GioKitDbManager.instance.deleteEvent(id)
    }

    @JvmStatic
    fun removeEvents(lastId: Long, type: String) {
        GioKitDbManager.instance.removeEvents(lastId, type)
    }
}