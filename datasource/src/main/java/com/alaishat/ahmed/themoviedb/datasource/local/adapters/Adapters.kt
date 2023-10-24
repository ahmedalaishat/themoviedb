package com.alaishat.ahmed.themoviedb.datasource.local.adapters

import app.cash.sqldelight.ColumnAdapter

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
val listOfIntegersAdapter = object : ColumnAdapter<List<Int>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",").map { it.toInt() }
        }

    override fun encode(value: List<Int>) = value.joinToString(separator = ",")
}