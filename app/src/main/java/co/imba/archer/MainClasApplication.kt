package co.imba.archer

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainClasApplication: Application() {
    companion object {
        const val AF_DEV_KEY = "GPct2GtaE3PvYamjPJFQRo"
        const val jsoupCheck = " 1f5b"
        const val ONESIGNAL_APP_ID = "beb3c748-28ab-44d2-afbc-53dd6704c959"

        val linkFilterPart1 = "http://burning"
        val linkFilterPart2 = "serendipity.xyz/go.php?to=1&"
        val linkAppsCheckPart1 = "http://burning"
        val linkAppsCheckPart2 = "serendipity.xyz/apps.txt"

        val odone = "sub_id_1="

        var MAIN_ID: String? = ""
        var C1: String? = "c11"
        var D1: String? = "d11"
        var CH: String? = "check"

    }

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch(Dispatchers.IO) {
            applyDeviceId(context = applicationContext)
        }
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        Hawk.init(this).build()
    }

    private suspend fun applyDeviceId(context: Context) {
        val advertisingInfo = Adv(context)
        val idInfo = advertisingInfo.getAdvertisingId()
        Hawk.put(MAIN_ID, idInfo)
    }

}

class Adv (context: Context) {
    private val adInfo = AdvertisingIdClient(context.applicationContext)

    suspend fun getAdvertisingId(): String =
        withContext(Dispatchers.IO) {
            adInfo.start()
            val adIdInfo = adInfo.info
            adIdInfo.id
        }
}