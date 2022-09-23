package com.nedaluof.data.repository.app

import com.nedaluof.data.datasource.local.PreferencesManager
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/28/2022.
 */
class AppRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : AppRepository {

    override fun setNightModeEnabled(enable: Boolean) {
        preferencesManager.setNightModeEnabled(enable)
    }

    override fun isNightModeEnabled():Boolean {
        return preferencesManager.isNightModeEnabled()
    }
}