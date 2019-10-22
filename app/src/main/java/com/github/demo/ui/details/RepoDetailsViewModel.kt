package com.github.demo.ui.details

import com.github.demo.data.models.Repository
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseViewModel
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class RepoDetailsViewModel(
    compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    val githubRepository: GithubRepository
) : BaseViewModel(compositeDisposable, schedulerProvider, networkHelper) {

    override fun onCreate() {}

    fun saveToDb(data: Repository) {
        compositeDisposable.addAll(
            githubRepository.saveRepos(data)
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe({}, {})
        )
    }

}