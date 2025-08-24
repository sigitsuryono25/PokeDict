package com.surelabsid.pokeinfo.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.surelabsid.core.data.common.Constants
import com.surelabsid.core.data.source.local.LocalDataSource
import com.surelabsid.core.data.source.local.room.PokemonDatabase
import com.surelabsid.core.data.source.network.services.PokeService
import com.surelabsid.core.domain.usecase.PokeInteractor
import com.surelabsid.core.domain.usecase.PokeUseCase
import com.surelabsid.core.utils.helpers.CacheInterceptor
import com.surelabsid.pokeinfo.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java, "pokemon.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single { get<PokemonDatabase>().appDao() }
}
var networkModules = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(CacheInterceptor(androidContext()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    single {
        GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
        retrofit.create(PokeService::class.java)
    }
}

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
}

val useCaseModules = module {
    single<PokeUseCase> { PokeInteractor(get()) }
}

val repositoryModules = module {
    single { LocalDataSource(get(), get(), get()) }
}