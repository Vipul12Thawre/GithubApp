package com.github.demo.ui.readMe

import androidx.lifecycle.MutableLiveData
import com.github.demo.data.models.ReadMeDetails
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseViewModel
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ReadMeViewModel(
    compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    private val githubRepository: GithubRepository
) : BaseViewModel(compositeDisposable, schedulerProvider, networkHelper) {

    val readMeLiveData = MutableLiveData<ReadMeDetails>()

    override fun onCreate() {}

    fun getReadMe(owner: String, repoName: String) {

        compositeDisposable.addAll(

            githubRepository.getReadMe("$owner/$repoName")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    it.run {
                        readMeLiveData.postValue(this)
                        callFromNetwork(owner, repoName)
                    }

                }, {
                    callFromNetwork(owner, repoName)
                })
        )
    }


    private fun callFromNetwork(owner: String, repoName: String) {

        compositeDisposable.addAll(
            githubRepository.doFetchReadMe(owner, repoName)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({

                    it.run {
                        readMeLiveData.postValue(this)
                        githubRepository.saveReadMeDetails(
                            ReadMeDetails(
                                "$owner/$repoName",
                                it.downloadUrl
                            )
                        ).subscribeOn(schedulerProvider.computation())
                            .observeOn(schedulerProvider.ui())
                            .subscribe({},{})
                    }
                }, {
                    handleException(it)
                })
        )

    }
}