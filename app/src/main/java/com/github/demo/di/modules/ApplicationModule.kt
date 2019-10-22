package com.github.demo.di.modules


import androidx.room.Room
import com.github.demo.BuildConfig
import com.github.demo.GitApp
import com.github.demo.data.local.db.DataBaseService
import com.github.demo.data.remote.NetworkService
import com.github.demo.data.remote.Networking
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.RxSchdulerProvider
import com.github.demo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class ApplicationModule(private val application: GitApp) {

    @Provides
    fun provideContext() = application

    @Provides
    @Singleton
    open fun provideNetworkService(cacheInterceptor: Interceptor): NetworkService =
        Networking.create(
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024,
            cacheInterceptor
        )

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideNetworkHelper() = NetworkHelper(application)

    @Provides
    fun provideScheduler(): SchedulerProvider = RxSchdulerProvider()

    @Provides
    open fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.MINUTES)
                .build()

            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideDatabaseService() = Room.databaseBuilder(
        application,
        DataBaseService::class.java,
        "gitDb"
    ).build()

}
