package diolink.library.lili.apsik

import android.app.Activity
import android.content.Context
import diolink.library.lili.bd.LinkDatabase
import diolink.library.lili.bd.Repository
import diolink.library.lili.bd.StorageUtils
import diolink.library.lili.one.Constants
import diolink.library.lili.one.Constants.appsDevKey
import diolink.library.lili.one.FirebaseRemoteListener
import diolink.library.lili.one.OneSignalManager

object Projector {


    lateinit var preferences: StorageUtils.Preferences
    var repository: Repository? = null



    fun createRemoteConfigInstance(activity: Activity): FirebaseRemoteListener {
        preferences = StorageUtils.Preferences(
            activity, Constants.NAME,
            Constants.MAINKEY,
            Constants.CHYPRBOOL
        )
        return FirebaseRemoteListener(activity)
    }

    fun createAppsInstance(context: Context, devKey: String): Manager {
        appsDevKey = devKey
       return Manager(context, devKey)
    }

    fun createOneSignalInstance(context: Context, oneSignalId: String): OneSignalManager {
        return OneSignalManager(context, oneSignalId)
    }

    fun createRepoInstance(context: Context): Repository {
        if (repository == null){
            return Repository(LinkDatabase.getDatabase(context).linkDao())
        } else {
            return repository as Repository
        }
    }

}