//package com.alaishat.ahmed.themoviedb.data.source.remote.paging
//
//import com.alaishat.ahmed.themoviedb.data.mapper.toDomainException
//
///**
// * Created by Ahmed Al-Aishat on Sep/15/2023.
// * The Movie DB Project.
// */
//open class CacheablePagingSource<Value : Any>(
//    override val pageDataProvider: suspend (page: Int) -> List<Value>,
//    private val pageCachingHandler: suspend (page: Int, pageData: List<Value>) -> Unit,
//) : BasePagingSource<Value>(pageDataProvider = pageDataProvider) {
//
//
//    override suspend fun loadUiState(params: LoadParams<Int>): LoadResult<Int, Value> {
//        return try {
//            val page = params.key ?: 1
//            val fetchedPageData = pageDataProvider(page)
//            pageCachingHandler(page, fetchedPageData)
//
//            resultOf(page = page, fetchedPage = fetchedPageData)
//        } catch (e: Exception) {
//            LoadResult.Error(e.toDomainException())
//        }
//    }
//}