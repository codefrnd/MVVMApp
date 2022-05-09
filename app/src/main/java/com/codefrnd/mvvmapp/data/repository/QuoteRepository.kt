package com.codefrnd.mvvmapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codefrnd.mvvmapp.data.db.AppDB
import com.codefrnd.mvvmapp.data.db.entities.Quote
import com.codefrnd.mvvmapp.data.network.MyApi
import com.codefrnd.mvvmapp.data.network.SafeApiRequest
import com.codefrnd.mvvmapp.uitl.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteRepository(
    private val api : MyApi,
    private val db : AppDB
) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    private suspend fun fetchQuotes() {
        if(isFetchNeeded()) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    suspend fun getQuotes() : LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private fun isFetchNeeded() : Boolean {
        return true
    }

    private fun saveQuotes(quotes : List<Quote>){
        Coroutines.io {
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}