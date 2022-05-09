package com.codefrnd.mvvmapp.data.network.response

import com.codefrnd.mvvmapp.data.db.entities.Quote

data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)