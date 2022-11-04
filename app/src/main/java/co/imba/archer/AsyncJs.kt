package co.imba.archer

import android.content.Context
import android.util.Log
import co.imba.archer.MainClasApplication.Companion.C1
import co.imba.archer.MainClasApplication.Companion.D1
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class AsyncJs(val context: Context) {
    private var jsoup: String = ""

    suspend fun coTask(): String {

        val nameParameter: String? = Hawk.get(C1)
        val appLinkParameter: String? = Hawk.get(D1)


        val taskName =
            "${MainClasApplication.linkFilterPart1}${MainClasApplication.linkFilterPart2}${MainClasApplication.odone}$nameParameter"
        val taskLink =
            "${MainClasApplication.linkFilterPart1}${MainClasApplication.linkFilterPart2}${MainClasApplication.odone}$appLinkParameter"

        withContext(Dispatchers.IO) {
            //changed logical null to string null
            if (nameParameter != "null") {
                getDocSecretKey(taskName)
//                Log.d("Check1C", taskName)
            } else {
                getDocSecretKey(taskLink)
//                Log.d("Check1C", taskLink)
            }
        }
        return jsoup
    }

    private fun getDocSecretKey(link: String) {
        val text = Jsoup.connect(link).get().text()
        Log.d("Text from jsoup", text)
        jsoup = text
    }
}