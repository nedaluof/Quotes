package com.nedaluof.domain.usecases.base

import com.nedaluof.data.datasource.remote.apiresponse.ApiError
import com.nedaluof.domain.util.handleException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.concurrent.CancellationException

/**
 * Created by NedaluOf on 8/10/2021.
 * Base UseCase
 * must inherited from all Use Cases that required remote call
 * */
abstract class BaseUseCase {
  /**
   * [TYPE] : represent the response data type
   * @param scope : Coroutine scope to run in
   * @param apiToCall : api that will be executed
   * @param onLoading : expose loading status from use-case to the viewModel
   * @param onSuccess : expose data from use-case to the viewModel
   * @param onError : expose error description from use-case to the viewModel
   * */
  fun <TYPE> invoke(
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    apiToCall: suspend () -> Response<TYPE>,
    onLoading: (Boolean) -> Unit,
    onSuccess: (TYPE) -> Any,
    onError: (ApiError) -> Any
  ) {
    onLoading(true)
    scope.launch {
      try {
        val result = apiToCall()
        onSuccess.invoke(result.body()!!)
        onLoading(false)
      } catch (e: CancellationException) {
        onError.invoke(handleException(e))
        onLoading(false)
      } catch (e: Exception) {
        onError.invoke(handleException(e))
        onLoading(false)
      }
    }
  }
}