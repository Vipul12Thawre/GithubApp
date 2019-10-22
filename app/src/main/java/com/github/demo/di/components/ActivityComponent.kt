package com.github.demo.di.components


import com.github.demo.di.modules.ActivityModule
import com.github.demo.di.scopes.ActivityScope
import com.github.demo.ui.details.RepoDetailsActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {
     fun inject(repoDetailsActivity: RepoDetailsActivity)

}