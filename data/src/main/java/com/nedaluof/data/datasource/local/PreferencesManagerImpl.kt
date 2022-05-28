package com.nedaluof.data.datasource.local

import android.content.Context
import com.nedaluof.mihawk.MiHawk
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/28/2022.
 */
class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesManager {

    init {
        MiHawk.init(context)
    }

    override fun setNightModeEnabled(enable: Boolean) {
        MiHawk.put(NIGHT_MODE_KEY, enable)
    }

    override fun isNightModeEnabled(enabled: (Boolean?) -> Unit) {
        MiHawk.get<Boolean>(NIGHT_MODE_KEY, enabled)
    }

    companion object {
        private const val NIGHT_MODE_KEY = "NIGHT_MODE_KEY"
    }
}