package com.kongregate.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kongregate.mobile.MainClasApplication.Companion.jsoupCheck
import com.kongregate.mobile.onlygame.PlayGameActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FilterMePleaseActivity : AppCompatActivity() {
    lateinit var jsoup: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_me_please)
        jsoup = ""
        val asyncJs = AsyncJs(applicationContext)

        val job = GlobalScope.launch(Dispatchers.IO) {
            jsoup = asyncJs.coTask()
        }

        runBlocking {
            job.join()
            if (jsoup == jsoupCheck) {
                Intent(applicationContext, PlayGameActivity::class.java).also { startActivity(it) }
            } else {
                Intent(applicationContext, WeeeeebLaunchActivity::class.java).also { startActivity(it) }
            }
            finish()
        }
    }
}