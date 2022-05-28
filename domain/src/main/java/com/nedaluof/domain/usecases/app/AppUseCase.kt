package com.nedaluof.domain.usecases.app

/**
 * Created by NedaluOf on 5/28/2022.
 */
interface AppUseCase {
    fun updateNightModeEnabled(enable: Boolean)
    fun isNightModeEnabled(enabled: (Boolean) -> Unit)
}