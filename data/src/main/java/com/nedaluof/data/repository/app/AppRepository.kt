package com.nedaluof.data.repository.app

/**
 * Created by NedaluOf on 5/28/2022.
 */
interface AppRepository {
    fun setNightModeEnabled(enable: Boolean)
    fun isNightModeEnabled(enabled: (Boolean?) -> Unit)
}