package com.github.demo.di.modules

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseActivity
import com.github.demo.ui.contributors.ContributorsViewModel
import com.github.demo.ui.details.RepoDetailsViewModel
import com.github.demo.utils.ViewModelProviderFactory
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {


    @Provides
    fun provideLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)


    @Provides
    fun provideRepoDetailsViewModel(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper,
        githubRepository: GithubRepository
    ): RepoDetailsViewModel {
        return ViewModelProviders.of(
            activity,
            ViewModelProviderFactory(RepoDetailsViewModel::class) {
                RepoDetailsViewModel(
                    compositeDisposable,
                    schedulerProvider,
                    networkHelper,
                    githubRepository
                )
            }).get(RepoDetailsViewModel::class.java)
    }
}