package me.solhaug.news

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private const val LOG_TAG = "NEWS"

    fun log(msg: String) = Log.i(LOG_TAG, msg)
    fun error(msg: String) = Log.e(LOG_TAG, msg)

    fun utcToGmt(utcDate: String?): Date? {
        if (utcDate.isNullOrEmpty())
            return null

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        //dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.parse(utcDate)
    }

    inline fun <T>catchAllNull(action: () -> T) = try { action() } catch (t: Throwable) { null }
}
