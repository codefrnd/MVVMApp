package com.codefrnd.mvvmapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codefrnd.mvvmapp.data.db.AppDB
import com.codefrnd.mvvmapp.data.db.entities.Quote
import com.codefrnd.mvvmapp.data.network.MyApi
import com.codefrnd.mvvmapp.data.network.SafeApiRequest
import com.codefrnd.mvvmapp.data.network.response.QuotesResponse
import com.codefrnd.mvvmapp.data.preferences.PreferenceProvider
import com.codefrnd.mvvmapp.uitl.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

class QuoteRepository(
    private val api: MyApi,
    private val db: AppDB,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    private suspend fun fetchQuotes() {
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            /*val response = apiRequest { api.getQuotes() }*/

            val quote1 = Quote(
                1,
                "Don't cry because it's over, smile because it happened",
                "Dr. Seuss",
                "null",
                "2019-07-06 10:35:33",
                "2019-07-06 10:35:33"
            );
            val quote2 = Quote(
                2,
                "Don't cry because it's over, smile because it happened",
                "Dr. Seuss",
                "null",
                "2019-07-06 10:35:33",
                "2019-07-06 10:35:33"
            );

            val quote3 = Quote(
                3,
                "Don't cry because it's over, smile because it happened",
                "Dr. Seuss",
                "null",
                "2019-07-06 10:35:33",
                "2019-07-06 10:35:33"
            );

            val quote4 = Quote(
                4,
                "Don't cry because it's over, smile because it happened",
                "Dr. Seuss",
                "null",
                "2019-07-06 10:35:33",
                "2019-07-06 10:35:33"
            );

            val quotesDemo: List<Quote> = listOf(quote1, quote2, quote3, quote4);
            val response = QuotesResponse(true, quotesDemo)
            quotes.postValue(response.quotes)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefs.saveLastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}