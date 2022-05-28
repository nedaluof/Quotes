package com.nedaluof.domain.usecases.base

import com.nedaluof.domain.util.handleException
import com.nedaluof.data.datasource.remote.apiresponse.ApiError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

/**
 * Created by NedaluOf on 8/10/2021.
 * Base UseCase; must inherited from all UseCases
 * **********************************************
 * Params here represent inputs to the functions/Apis if exist
 * */
abstract class BaseUseCase<Type, in Params>() where Type : Any {

    abstract suspend fun run(params: Params? = null): Type

    /**
     * called from view model classes
     * */
    fun invoke(
        scope: CoroutineScope,
        params: Params?,
        onSuccess: (Type) -> Any,
        onError: (ApiError) -> Any
    ) {
        scope.launch {
            try {
                val result = run(params)
                onSuccess.invoke(result)
            } catch (e: CancellationException) {
                e.printStackTrace()
                onError.invoke(handleException(e))
            } catch (e: Exception) {
                e.printStackTrace()
                onError.invoke(handleException(e))
            }
        }
    }
}