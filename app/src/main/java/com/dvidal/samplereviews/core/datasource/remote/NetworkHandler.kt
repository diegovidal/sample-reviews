package com.dvidal.samplereviews.core.datasource.remote

import android.content.Context
import com.dvidal.samplereviews.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-02-15.
 */

@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}
