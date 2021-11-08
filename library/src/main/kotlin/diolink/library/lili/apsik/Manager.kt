package diolink.library.lili.apsik

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import diolink.library.lili.*

import diolink.library.lili.apsik.Projector.preferences
import diolink.library.lili.bd.Link
import diolink.library.lili.bd.RemoteListenerCallback
import diolink.library.lili.one.Constants
import diolink.library.lili.one.Constants.LOG
import diolink.library.lili.one.Constants.ONCONVERSION
import diolink.library.lili.one.Constants.TAG
import diolink.library.lili.one.Constants.TRUE

class Manager(private val context: Context, private val appsDevKey: String):
    RemoteListenerCallback {

    private lateinit var appsflyer: Appsflyer

    fun start(offerUrl: String) {

        appsflyer = context as Appsflyer

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                data?.let { cvData ->
                    cvData.map {
                        if (LOG) Log.d(TAG, "got apps Data - succes conversion")
                        when (preferences.getOnConversionDataSuccess(ONCONVERSION)) {
                            "null" -> {
                                preferences.setOnConversionDataSuccess(
                                    ONCONVERSION,
                                    TRUE
                                )
                                if (LOG) Log.d(TAG, "got apps Data - $data")
                                if (data["campaign"].toString().contains("sub")) {

                                    val url = Utils.getFinalUrl(
                                        offerUrl,
                                        data["campaign"].toString(),
                                        context, data["af_c_id"].toString(),
                                        data["media_source"].toString(),
                                    )


                                    if (LOG) Log.d(TAG, "$url -- final url")
                                    Projector.createRepoInstance(context).insert(Link(1, url))
                                    //   if (LOG) Log.d(TAG, "added to viewmodel number 1")
                                    appsflyer.onConversionDataSuccess(data, url)

                                } else {
                                    preferences.setOnConversionDataSuccess(
                                        ONCONVERSION,
                                        Constants.TRUE
                                    )
                                    val url = offerUrl + "?app_id=" + Utils.getAppBundle(context) +
                                            "&af_status=" + "Organic" +
                                            "&afToken=" + appsDevKey +
                                            "&afid=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context)
                                    //  if (LOG) Log.d(TAG, "url - $url")
                                    Projector.createRepoInstance(context).insert(Link(1, url))
                                    //  if (LOG) Log.d(TAG, "added to viewmodel number 2")
                                    appsflyer.onConversionDataSuccess(data, url)
                                }
                            }
                            "true" -> {

                            }
                            "false" -> {

                            }
                            else -> {

                            }
                        }


                    }
                }
            }

            override fun onConversionDataFail(error: String?) {
                if (LOG) Log.d(TAG, "onConversionDataFail")
                appsflyer.onConversionDataFail(error)
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    if (LOG) Log.d(TAG, "onAppOpenAttribution")
                }
            }

            override fun onAttributionFailure(error: String?) {
                if (LOG) Log.d(TAG, "onAttributionFailure")
            }
        }
        AppsFlyerLib.getInstance().init(appsDevKey, conversionDataListener, context)
        AppsFlyerLib.getInstance().start(context)
    }

    override fun onFalseCode(int: Int) {

    }

    override fun onSuccessCode(offerUrl: String) {
        Log.d(TAG, "onSuccessCode AppsFlyer Class")
        start(offerUrl)
    }

    override fun onStatusTrue() {

    }

    override fun onStatusFalse() {

    }

    override fun nonFirstLaunch(url: String) {

    }

    override fun onDeepLinkSuccess(
        fbappid: String,
        fbappsecret: String,
        offerUrl: String,
        naming: String
    ) {

    }

}