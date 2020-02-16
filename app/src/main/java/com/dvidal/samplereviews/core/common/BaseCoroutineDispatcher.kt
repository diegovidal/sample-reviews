package com.dvidal.samplereviews.core.common

import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class BaseCoroutineDispatcher @Inject constructor() {

    fun Main() = Dispatchers.Main
    fun IO() = Dispatchers.IO
}