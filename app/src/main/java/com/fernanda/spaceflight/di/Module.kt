package com.fernanda.spaceflight.di


import com.fernanda.spaceflight.network.ApiService
import com.fernanda.spaceflight.repository.NewRepository
import com.fernanda.spaceflight.ui.home.HomeViewModel
import com.fernanda.spaceflight.ui.dialog.DialogDetailViewModel
import com.fernanda.spaceflight.ui.dialog.error.DialogErrorViewModel
import com.fernanda.spaceflight.ui.fragment.NewsViewModel
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
    single { provideOkhttp(get()) }
    single { provideRetrofit(get()) }

}
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { NewsViewModel(get()) }
    viewModel { DialogDetailViewModel(get()) }
    viewModel { DialogErrorViewModel(get()) }
}
val repositoryModule = module {
    factory { NewRepository(get()) }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

private fun provideOkhttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
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