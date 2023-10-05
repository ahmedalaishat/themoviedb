package com.alaishat.ahmed.themoviedb.data.source.remote.paging

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
class NormalPagingSource<Value : Any>(
    pageDataProvider: suspend (page: Int) -> List<Value>,
) : BasePagingSource<Value>(pageDataProvider = pageDataProvider)