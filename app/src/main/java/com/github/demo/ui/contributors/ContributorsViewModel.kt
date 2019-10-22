package com.github.demo.ui.contributors

import androidx.lifecycle.MutableLiveData
import com.github.demo.data.models.Contributor
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseViewModel
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ContributorsViewModel(
    compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    private val githubRepository: GithubRepository
) : BaseViewModel(compositeDisposable, schedulerProvider, networkHelper) {

    val constributorsLiveData = MutableLiveData<List<Contributor>>()

    override fun onCreate() {

    }

    fun getContributors(owner: String, repoName: String) {

        compositeDisposable.addAll(

            githubRepository.getContributorsList("$owner/$repoName")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    constributorsLiveData.postValue(it)
                    callContributorsFromNetwork(owner, repoName)
                }, {
                    callContributorsFromNetwork(owner, repoName)
                })
        )
    }

    private fun callContributorsFromNetwork(owner: String, repoName: String) {
        compositeDisposable.addAll(

            githubRepository.doFetchContributors(owner, repoName)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    constributorsLiveData.postValue(it)

                    githubRepository.saveContributorsList(it)
                        .subscribeOn(schedulerProvider.computation())
                        .observeOn(schedulerProvider.ui()).subscribe({}, {})
                }, {
                    handleException(it)
                })
        )
    }
}