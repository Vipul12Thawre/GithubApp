package com.github.demo.ui.userDetails

import androidx.lifecycle.MutableLiveData
import com.github.demo.data.models.Contributor
import com.github.demo.data.remote.response.owner.OwnerDetailsResponse
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseViewModel
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class OwnDetailsViewModel(
    compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    private val githubRepository: GithubRepository
) : BaseViewModel(compositeDisposable, schedulerProvider, networkHelper) {

    val ownerLiveData = MutableLiveData<OwnerDetailsResponse>()

    override fun onCreate() {}

    fun getOwnerDetails(owner: String) {

        compositeDisposable.addAll(
            githubRepository.getUserFromDb(owner)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    ownerLiveData.postValue(it)
                    callUserDetailsFromNetwork(owner)
                }, {
                    callUserDetailsFromNetwork(owner)
                })
        )
    }

    private fun callUserDetailsFromNetwork(owner: String) {

        compositeDisposable.addAll(
            githubRepository.doFetchUserDetails(owner)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    it?.run {
                        ownerLiveData.postValue(this)
                        githubRepository.saveUserToDb(this)
                            .subscribeOn(schedulerProvider.computation())
                            .observeOn(schedulerProvider.ui())
                            .subscribe({},{})
                    }
                }, {
                    handleException(it)
                })
        )

    }
}