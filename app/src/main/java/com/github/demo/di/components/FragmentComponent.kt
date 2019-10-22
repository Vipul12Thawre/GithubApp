package com.github.demo.di.components


import com.github.demo.di.modules.FragmentModule
import com.github.demo.di.scopes.FragmentScope
import com.github.demo.ui.contributors.ContributorsListFragment
import com.github.demo.ui.offlineRepos.OfflineReposFragment
import com.github.demo.ui.publicRepo.PublicRepoListFragment
import com.github.demo.ui.readMe.ReadMeFragment
import com.github.demo.ui.search.SearchRepoFragment
import dagger.Component

@FragmentScope
@Component(modules = [FragmentModule::class], dependencies = [ApplicationComponent::class])
interface FragmentComponent {
     fun inject(publicRepoListFragment: PublicRepoListFragment)
     fun inject(offlineReposFragment: OfflineReposFragment)
     fun inject(searchRepoFragment: SearchRepoFragment)
     fun inject(contributorsListFragment: ContributorsListFragment)
     fun inject(readMeFragment: ReadMeFragment)

}