package com.codefrnd.mvvmapp

import android.app.Application
import com.codefrnd.mvvmapp.data.db.AppDB
import com.codefrnd.mvvmapp.data.network.MyApi
import com.codefrnd.mvvmapp.data.network.NetworkConnectionInterceptor
import com.codefrnd.mvvmapp.data.repository.QuoteRepository
import com.codefrnd.mvvmapp.data.repository.UserRepository
import com.codefrnd.mvvmapp.ui.auth.AuthViewModel
import com.codefrnd.mvvmapp.ui.auth.AuthViewModelFactory
import com.codefrnd.mvvmapp.ui.home.profile.ProfileViewModelFactory
import com.codefrnd.mvvmapp.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApp))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDB(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuoteRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }
    }
}