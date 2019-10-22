package com.github.demo.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.demo.GitApp
import com.github.demo.R
import com.github.demo.di.components.*
import com.github.demo.di.modules.ActivityModule
import com.github.demo.di.modules.BottomSheetDialogFragmentModule
import com.github.demo.di.modules.FragmentModule
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment<VM : BaseViewModel> : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModel: VM

    @LayoutRes
    abstract fun getLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

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

    abstract fun injectDependency(component: BottomSheetDialogFragmentComponent)

    private fun buildDependency() = DaggerBottomSheetDialogFragmentComponent.builder()
        .bottomSheetDialogFragmentModule(BottomSheetDialogFragmentModule(this))
        .applicationComponent((context!!.applicationContext as GitApp).component)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireDialog() as BottomSheetDialog).dismissWithAnimation = true
    }
}