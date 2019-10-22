package com.github.demo.di.components


import com.github.demo.di.modules.BottomSheetDialogFragmentModule
import com.github.demo.di.modules.FragmentModule
import com.github.demo.di.scopes.BottomSheetDialogFragmentScope
import com.github.demo.di.scopes.FragmentScope
import com.github.demo.ui.contributors.ContributorsListFragment
import com.github.demo.ui.offlineRepos.OfflineReposFragment
import com.github.demo.ui.publicRepo.PublicRepoListFragment
import com.github.demo.ui.readMe.ReadMeFragment
import com.github.demo.ui.search.SearchRepoFragment
import com.github.demo.ui.userDetails.OwnerDetailsBottomSheetDialogFragment
import dagger.Component

@BottomSheetDialogFragmentScope
@Component(
    modules = [BottomSheetDialogFragmentModule::class],
    dependencies = [ApplicationComponent::class]
)
interface BottomSheetDialogFragmentComponent {
    fun inject(ownerDetailsBottomSheetDialogFragment: OwnerDetailsBottomSheetDialogFragment)

}