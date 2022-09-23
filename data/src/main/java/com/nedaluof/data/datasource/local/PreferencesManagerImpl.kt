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
    MiHawk.Builder(context)
      .withPreferenceName("quotes_app")
      .build()
  }

  override fun setNightModeEnabled(enable: Boolean) {
    MiHawk.put(NIGHT_MODE_KEY, enable)
  }

  override fun isNightModeEnabled(): Boolean {
    return MiHawk.get(NIGHT_MODE_KEY, false)
  }

  companion object {
    private const val NIGHT_MODE_KEY = "NIGHT_MODE_KEY"
  }
}