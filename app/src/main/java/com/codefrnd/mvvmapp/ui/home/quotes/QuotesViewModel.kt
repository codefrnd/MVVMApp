package com.codefrnd.mvvmapp.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.codefrnd.mvvmapp.data.repository.QuoteRepository
import com.codefrnd.mvvmapp.uitl.lazyDeferred

class QuotesViewModel(
    repository: QuoteRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}