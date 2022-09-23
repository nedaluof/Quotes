package com.nedaluof.data.datasource.local

/**
 * Created by NedaluOf on 5/28/2022.
 */
interface PreferencesManager {
    fun setNightModeEnabled(enable: Boolean)
    fun isNightModeEnabled():Boolean
}