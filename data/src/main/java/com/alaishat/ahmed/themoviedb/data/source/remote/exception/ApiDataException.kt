package com.alaishat.ahmed.themoviedb.data.source.remote.exception

import com.alaishat.ahmed.themoviedb.data.architecture.exception.DataException

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
class ApiDataException(override val message: String? = null) : DataException()