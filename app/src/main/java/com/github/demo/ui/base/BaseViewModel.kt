package com.github.demo.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.demo.R
import com.github.demo.utils.common.Resource
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    val compositeDisposable: CompositeDisposable,
    schedulerProvider: SchedulerProvider,
    val networkHelper: NetworkHelper
) : ViewModel() {

    abstract fun onCreate()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()

    protected fun handleException(err: Throwable?) =
        err?.let {
            messageStringId.postValue(
                Resource.error(
                    R.string.network_default_error
                )
            )
        }

}