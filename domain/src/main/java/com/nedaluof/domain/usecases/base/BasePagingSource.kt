package com.nedaluof.domain.usecases.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response

/**
 * Created by NedaluOf on 5/28/2022.
 */
abstract class BasePagingSource<T : Any, R : Any> : PagingSource<Int, T>() {

    abstract suspend fun apiToCall(page: Int): Response<R>

    abstract fun mapData(body: R?): List<T>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val startIndex = 1
        val page = params.key ?: startIndex
        return try {
            val response = apiToCall(page)
            val data = mapData(response.body())
            LoadResult.Page(
                data = data,
                prevKey = if (page == startIndex) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
}