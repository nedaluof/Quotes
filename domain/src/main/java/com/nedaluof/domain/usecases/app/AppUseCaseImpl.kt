package com.nedaluof.domain.usecases.app

import com.nedaluof.data.repository.app.AppRepository
import javax.inject.Inject

/**
 * Created by NedaluOf on 5/28/2022.
 */
class AppUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : AppUseCase {

    override fun updateNightModeEnabled(enable: Boolean) {
        repository.setNightModeEnabled(enable)
    }

    override fun isNightModeEnabled(enabled: (Boolean) -> Unit) {
        repository.isNightModeEnabled { isEnabled ->
            enabled(isEnabled ?: false)
        }
    }
}