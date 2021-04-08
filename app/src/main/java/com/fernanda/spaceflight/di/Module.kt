package com.fernanda.spaceflight.di

import com.fernanda.spaceflight.network.ApiService
import com.fernanda.spaceflight.repository.NewsRepository
import com.fernanda.spaceflight.ui.HomeViewModel
import com.fernanda.spaceflight.utils.Constant.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttp(get()) }
    single { provideRetrofit(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }

}
val repositoryModule = module {
    factory { NewsRepository(get()) }

}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.apply {
        addInterceptor(httpLoggingInterceptor)
    }
    return okHttpClient.build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
    val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    return retrofit.create(ApiService::class.java)

}