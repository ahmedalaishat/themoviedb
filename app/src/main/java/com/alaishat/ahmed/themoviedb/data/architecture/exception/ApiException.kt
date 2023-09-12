package com.alaishat.ahmed.themoviedb.data.architecture.exception

import java.io.IOException

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class ApiException(override val message: String?) : IOException()