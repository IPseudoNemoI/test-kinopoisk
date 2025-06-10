package dev.pseudo.testkinopoisk.di

import dev.pseudo.testkinopoisk.data.remote.FilmsApiService
import dev.pseudo.testkinopoisk.data.repository.FilmRepositoryImpl
import dev.pseudo.testkinopoisk.domain.repository.FilmRepository
import dev.pseudo.testkinopoisk.presentation.viewmodel.FilmListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmsApiService::class.java)
    }

    single<FilmRepository> { FilmRepositoryImpl(get()) }

    viewModel { FilmListViewModel(get()) }
    //viewModel { FilmDetailViewModel() }
}