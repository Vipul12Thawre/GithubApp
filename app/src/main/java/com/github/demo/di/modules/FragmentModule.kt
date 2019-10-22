package com.github.demo.di.modules

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseFragment
import com.github.demo.ui.contributors.ContributorsViewModel
import com.github.demo.ui.offlineRepos.OfflineReposViewModel
import com.github.demo.ui.publicRepo.PublicRepoListViewModel
import com.github.demo.ui.readMe.ReadMeViewModel
import com.github.demo.ui.search.SearchRepoViewModel
import com.github.demo.utils.ViewModelProviderFactory
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {


    @Provides
    fun provideLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    fun providePublicRepoViewModel(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper,
        githubRepository: GithubRepository

    ): PublicRepoListViewModel {
        return ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(PublicRepoListViewModel::class) {
                PublicRepoListViewModel(
                    compositeDisposable,
                    schedulerProvider,
                    networkHelper,
                    githubRepository
                )
            }).get(PublicRepoListViewModel::class.java)
    }

    @Provides
    fun provideOfflineReposViewModel(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper,
        githubRepository: GithubRepository
    ): OfflineReposViewModel {
        return ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(OfflineReposViewModel::class) {
                OfflineReposViewModel(
                    compositeDisposable,
                    schedulerProvider,
                    networkHelper,
                    githubRepository
                )
            }).get(OfflineReposViewModel::class.java)
    }

    @Provides
    fun provideSearchRepoViewModel(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper,
        githubRepository: GithubRepository
    ): SearchRepoViewModel {
        return ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(SearchRepoViewModel::class) {
                SearchRepoViewModel(
                    compositeDisposable,
                    schedulerProvider,
                    networkHelper,
                    githubRepository
                )
            }).get(SearchRepoViewModel::class.java)
    }

    @Provides
    fun provideContributorsViewModel(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper,
        githubRepository: GithubRepository
    ): ContributorsViewModel {
        return ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(ContributorsViewModel::class) {
                ContributorsViewModel(
                    compositeDisposable,
                    schedulerProvider,
                    networkHelper,
                    githubRepository
                )
            }).get(ContributorsViewModel::class.java)
    }

    @Provides
    fun provideReadMeViewModel(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper,
        githubRepository: GithubRepository
    ): ReadMeViewModel {
        return ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(ReadMeViewModel::class) {
                ReadMeViewModel(
                    compositeDisposable,
                    schedulerProvider,
                    networkHelper,
                    githubRepository
                )
            }).get(ReadMeViewModel::class.java)
    }
}