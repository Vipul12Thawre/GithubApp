package com.github.demo.di.components

import com.github.demo.GitApp
import com.github.demo.data.local.db.DataBaseService
import com.github.demo.data.remote.NetworkService
import com.github.demo.data.repository.GithubRepository
import com.github.demo.di.modules.ApplicationModule
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: GitApp)

    fun getDisposable(): CompositeDisposable

    fun getNetworkHelper(): NetworkHelper

    fun getScheduler(): SchedulerProvider

    fun getNetworkService(): NetworkService

    fun getGithUbRepository(): GithubRepository


}