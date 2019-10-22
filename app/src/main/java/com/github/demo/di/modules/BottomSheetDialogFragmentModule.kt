package com.github.demo.di.modules

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.demo.data.repository.GithubRepository
import com.github.demo.ui.base.BaseActivity
import com.github.demo.ui.base.BaseBottomSheetDialogFragment
import com.github.demo.ui.base.BaseFragment
import com.github.demo.ui.contributors.ContributorsViewModel
import com.github.demo.ui.offlineRepos.OfflineReposViewModel
import com.github.demo.ui.publicRepo.PublicRepoListViewModel
import com.github.demo.ui.readMe.ReadMeViewModel
import com.github.demo.ui.search.SearchRepoViewModel
import com.github.demo.ui.userDetails.OwnDetailsViewModel
import com.github.demo.utils.ViewModelProviderFactory
import com.github.demo.utils.network.NetworkHelper
import com.github.demo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class BottomSheetDialogFragmentModule(private val fragment: BaseBottomSheetDialogFragment<*>) {


    @Provides
    fun provideOwnDetailsViewModel(
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        networkHelper: NetworkHelper,
        githubRepository: GithubRepository
    ): OwnDetailsViewModel {
        return ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(OwnDetailsViewModel::class) {
                OwnDetailsViewModel(
                    compositeDisposable,
                    schedulerProvider,
                    networkHelper,
                    githubRepository
                )
            }).get(OwnDetailsViewModel::class.java)
    }
}