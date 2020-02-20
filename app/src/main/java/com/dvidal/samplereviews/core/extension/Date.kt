package com.dvidal.samplereviews.core.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author diegovidal on 2020-02-20.
 */

fun String.dateToRead(): String? {

    val formatNetworkDate = SimpleDateFormat(FORMAT_NETWORK_DATE, Locale.getDefault())
    val formatDateForRead = SimpleDateFormat(FORMAT_DATE_FOR_READ, Locale.getDefault())

    return formatNetworkDate.parse(this)?.let { networkDate ->
        formatDateForRead.format(networkDate)
    }
}

const val FORMAT_NETWORK_DATE = "yyyy-MM-dd'T'HH:mm:ss"
const val FORMAT_DATE_FOR_READ = "d MMM yyyy HH:mm:ss"