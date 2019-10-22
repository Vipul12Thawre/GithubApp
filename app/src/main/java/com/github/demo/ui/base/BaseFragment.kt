package com.github.demo.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.demo.GitApp
import com.github.demo.R
import com.github.demo.di.components.ActivityComponent
import com.github.demo.di.components.DaggerActivityComponent
import com.github.demo.di.components.DaggerFragmentComponent
import com.github.demo.di.components.FragmentComponent
import com.github.demo.di.modules.ActivityModule
import com.github.demo.di.modules.FragmentModule
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    @LayoutRes
    abstract fun getLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutResource(), container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency(buildDependency())
        super.onCreate(savedInstanceState)
        setupObservers()
        viewModel.onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        setupView(savedInstanceState)
    }

    open fun setAdapters() {

    }

    abstract fun injectDependency(component: FragmentComponent)

    private fun buildDependency() = DaggerFragmentComponent.builder()
        .fragmentModule(FragmentModule(this))
        .applicationComponent((context!!.applicationContext as GitApp).component)
        .build()


    open fun setupObservers() {
        viewModel.messageStringId.observe(this, Observer {
            it?.run {
                data?.run {
                    showMessage(getString(this))
                }
            }
        })
    }

    open fun showMessage(errorTitle: String) {
        Toast.makeText(activity, errorTitle, Toast.LENGTH_SHORT).show()
    }

    open fun setupView(savedInstanceState: Bundle?) {}
}