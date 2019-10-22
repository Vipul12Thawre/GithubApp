package com.github.demo.ui.publicRepo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.demo.data.models.Repository
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseViewModel
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class PublicRepoListViewModel(
    compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    private val githubRepository: GithubRepository
) : BaseViewModel(compositeDisposable, schedulerProvider, networkHelper) {

    val repoListLiveData = MutableLiveData<List<Repository>>()
    val nextUrl = MutableLiveData<String>()

    override fun onCreate() {
        getPublicRepos()
    }

    private fun getPublicRepos() {

        compositeDisposable.addAll(
            githubRepository.doFetchPublicRepo()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    it?.let {
                        nextUrl.value = it.next
                        repoListLiveData.postValue(it.repository)

                    }
                }, {
                    handleException(it)
                })
        )
    }

    fun loadNextPage() {

        compositeDisposable.addAll(
            nextUrl.value?.run {
                githubRepository.doFetchNextPublicRepo(this)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        it?.let {
                            nextUrl.value = it.next
                            repoListLiveData.postValue(it.repository)
                        }
                    }, {
                        handleException(it)
                    })
            }
        )
    }

}