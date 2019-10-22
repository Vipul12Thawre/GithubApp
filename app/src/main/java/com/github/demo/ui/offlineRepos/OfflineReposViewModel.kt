package com.github.demo.ui.offlineRepos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.demo.R
import com.github.demo.data.models.Repository
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseViewModel
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class OfflineReposViewModel(
    compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    val githubRepository: GithubRepository
) : BaseViewModel(compositeDisposable, schedulerProvider, networkHelper) {
    val repoListLiveData = MutableLiveData<List<Repository>>()

    val deleteRepoLiveData = MutableLiveData<Int>()

    override fun onCreate() {
        getSavedRepos()
    }

    private fun getSavedRepos() {
        compositeDisposable.addAll(
            githubRepository.getSavedRepos()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    repoListLiveData.postValue(it)

                }, {
                    handleException(it)
                })
        )
    }

    fun deleteRepo(repository: Repository) {
        compositeDisposable.addAll(
            githubRepository.deleteRepo(repository)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    deleteRepoLiveData.postValue(R.string.repoDeleted)
                }, {})
        )
    }

}