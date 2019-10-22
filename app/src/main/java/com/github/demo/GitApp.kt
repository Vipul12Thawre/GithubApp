package com.github.demo

import android.app.Application
import com.github.demo.di.components.ApplicationComponent
import com.github.demo.di.components.DaggerApplicationComponent
import com.github.demo.di.modules.ApplicationModule


open class GitApp : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        buildDependencies()
        super.onCreate()

    }

    private fun buildDependencies() {
        component = getAppComponent()
        component.inject(this)
    }

    open fun getAppComponent(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
}