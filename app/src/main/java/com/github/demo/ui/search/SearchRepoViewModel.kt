package com.github.demo.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.demo.data.models.Repository
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseViewModel
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchRepoViewModel(
    compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    val githubRepository: GithubRepository
) : BaseViewModel(compositeDisposable, schedulerProvider, networkHelper) {

    val repoListLiveData = MutableLiveData<List<Repository>>()
    override fun onCreate() {
        configureAutoComplete()
    }

    private val autoCompletePublishSubject = PublishSubject.create<String>()


    private fun configureAutoComplete() {
        compositeDisposable.addAll(
            autoCompletePublishSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter {
                    return@filter it.isNotEmpty()
                }
                .distinctUntilChanged().subscribeOn(schedulerProvider.io())
                .switchMap {
                    githubRepository.getSearchedUsers(it)
                }
                .switchMap {
                    Observable.fromIterable(it)
                        .switchMapSingle {
                            githubRepository.getRepositoriesByUserName(it.login)
                        }
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    repoListLiveData.postValue(it)
                }, {
                    handleException(it)
                })
        )
    }


    fun searchUsers(user: String) {
        autoCompletePublishSubject.onNext(user.trim())
    }

}