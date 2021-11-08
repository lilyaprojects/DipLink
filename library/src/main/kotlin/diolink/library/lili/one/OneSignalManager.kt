package diolink.library.lili.one

import android.content.Context
import com.onesignal.OneSignal

class OneSignalManager(private val context: Context, private val oneSignalID: String) {

    fun initialize(){
        OneSignal.initWithContext(context)
        OneSignal.setAppId(oneSignalID)
    }

}