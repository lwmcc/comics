package com.mccarty.comics.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.mccarty.comics.R
import java.math.BigInteger
import java.security.MessageDigest

object Utils {
    fun hashString(timeStamp: String, privateKey: String, publicKey: String): String {
        val input = timeStamp + privateKey + publicKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun networkIsAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = manager.getNetworkCapabilities(manager.activeNetwork)
        return network != null && network.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun concatPathParts(path: String?, ext: String?): String? = "$path.$ext"

    fun appendHttps(url: String?): String? = "https" + url?.subSequence(4, url.length)

    fun getAttribute(activity: Activity): String? {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getString(activity.getString(R.string.attribution_text), "")
    }
}