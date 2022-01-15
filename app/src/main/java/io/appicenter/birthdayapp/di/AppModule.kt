package io.appicenter.birthdayapp.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.appicenter.birthdayapp.BuildConfig
import io.appicenter.birthdayapp.feature_birthday_list.data.remote.UsersApi
import io.appicenter.birthdayapp.feature_birthday_list.data.repository.UsersRepositoryImpl
import io.appicenter.birthdayapp.feature_birthday_list.data.util.NetworkConfig.BASE_URL
import io.appicenter.birthdayapp.feature_birthday_list.domain.repository.UsersRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        val level: HttpLoggingInterceptor.Level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }

        loggingInterceptor.setLevel(level)
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }


    @Singleton
    @Provides
    fun provideUserRepository(api: UsersApi): UsersRepository {
        return UsersRepositoryImpl(api)
    }


}