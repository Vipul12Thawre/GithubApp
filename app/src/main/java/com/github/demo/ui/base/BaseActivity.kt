package com.github.demo.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.demo.GitApp
import com.github.demo.di.components.ActivityComponent
import com.github.demo.di.components.DaggerActivityComponent
import com.github.demo.di.modules.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    @LayoutRes
    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency(buildDependency())
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        setupObservers()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }

    abstract fun injectDependency(component: ActivityComponent)

    private fun buildDependency() = DaggerActivityComponent.builder()
        .activityModule(ActivityModule(this))
        .applicationComponent((application as GitApp).component)
        .build()


    open fun setupObservers() {
        viewModel.messageStringId.observe(this, Observer {
            it?.run {
//                data?.run {
//                    showMessage(getString(first), getString(second))
//                }
            }
        })
    }

    open fun showMessage(errorTitle: String, errorMessage: String) {}

    open fun setupView(savedInstanceState: Bundle?) {}
}