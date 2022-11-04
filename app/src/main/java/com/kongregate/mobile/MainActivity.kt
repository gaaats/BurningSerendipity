package com.kongregate.mobile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.kongregate.mobile.MainClasApplication.Companion.AF_DEV_KEY
import com.kongregate.mobile.MainClasApplication.Companion.C1
import com.kongregate.mobile.MainClasApplication.Companion.CH
import com.kongregate.mobile.MainClasApplication.Companion.D1
import com.kongregate.mobile.MainClasApplication.Companion.linkAppsCheckPart1
import com.kongregate.mobile.MainClasApplication.Companion.linkAppsCheckPart2
import com.kongregate.mobile.databinding.ActivityMainBinding
import com.kongregate.mobile.onlygame.PlayGameActivity
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var bindMain: ActivityMainBinding

    var checker: String = "null"
    lateinit var jsoup: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindMain.root)
        jsoup = ""

        deePP(this)

        val prefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE)
        if (prefs.getBoolean("activity_exec", false)) {

            when (Hawk.get<String>(CH)) {
                "2" -> {
                    skipMe()
                }
                else -> {
                    toTestGrounds()
                }
            }
        } else {
            val exec = prefs.edit()
            exec.putBoolean("activity_exec", true)
            exec.apply()

            val job = GlobalScope.launch(Dispatchers.IO) {
                checker = getCheckCode(linkAppsCheckPart1+linkAppsCheckPart2)
            }
            runBlocking {
                try {
                    job.join()
                } catch (_: Exception){
                }
            }

            when (checker) {
                "1" -> {
                    AppsFlyerLib.getInstance()
                        .init(AF_DEV_KEY, conversionDataListener, applicationContext)
                    AppsFlyerLib.getInstance().start(this)
                    afNullRecordedOrNotChecker(1500)
                }
                "2" -> {
                    skipMe()

                }
                "0" -> {
                    toTestGrounds()
                }
            }
        }
    }



    private suspend fun getCheckCode(link: String): String {
        val url = URL(link)
        val oneStr = "1"
        val twoStr = "2"
        val activeStrn = "0"
        val urlConnection = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection

        return try {
            when (val text = urlConnection.inputStream.bufferedReader().readText()) {
                "2" -> {
                    Hawk.put(CH, twoStr)
                    twoStr
                }
                "1" -> {
                    oneStr
                }
                else -> {
                    activeStrn
                }
            }
        } finally {
            urlConnection.disconnect()
        }

    }

    private fun afNullRecordedOrNotChecker(timeInterval: Long): Job {

        return CoroutineScope(Dispatchers.IO).launch {
            while (NonCancellable.isActive) {
                val hawk1: String? = Hawk.get(C1)
                if (hawk1 != null) {
                    toTestGrounds()
                    break
                } else {
                    val hawk1: String? = Hawk.get(C1)
                    delay(timeInterval)
                }
            }
        }
    }



    val conversionDataListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {

            val dataGotten = data?.get("campaign").toString()
            Hawk.put(C1, dataGotten)
        }

        override fun onConversionDataFail(p0: String?) {

        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

        }

        override fun onAttributionFailure(p0: String?) {
        }
    }

    private fun toTestGrounds() {
        Intent(this, FilterMePleaseActivity::class.java)
            .also { startActivity(it) }
        finish()
    }

    private fun skipMe() {
        Intent(this, PlayGameActivity::class.java)
            .also { startActivity(it) }
        finish()
    }
    fun deePP(context: Context) {

        AppLinkData.fetchDeferredAppLinkData(
            context
        ) { appLinkData: AppLinkData? ->
            appLinkData?.let {
                val params = appLinkData.targetUri.host
                Hawk.put(D1,params.toString())
            }
        }
    }
}