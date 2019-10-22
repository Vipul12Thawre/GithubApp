package com.github.demo.ui.readMe

import android.os.Bundle
import androidx.lifecycle.Observer
import br.tiagohm.markdownview.css.ExternalStyleSheet
import com.github.demo.R
import com.github.demo.di.components.FragmentComponent
import com.github.demo.ui.base.BaseFragment

import kotlinx.android.synthetic.main.fragment_readme.*


class ReadMeFragment : BaseFragment<ReadMeViewModel>() {


    companion object {
        private const val OWNER = "OWNER"
        private const val REPO_NAME = "REPO_NAME"

        fun newInstance(owner: String, repoName: String): ReadMeFragment {

            val args = Bundle()
            args.run {
                putString(OWNER, owner)
                putString(REPO_NAME, repoName)
            }
            val fragment = ReadMeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResource() = R.layout.fragment_readme

    override fun injectDependency(component: FragmentComponent) {
        component.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        markdown_view.addStyleSheet(ExternalStyleSheet.fromAsset("darkdown.css", null))
        arguments?.run {
            viewModel.getReadMe(getString(OWNER)!!, getString(REPO_NAME)!!)
        }
    }


    override fun setupObservers() {
        super.setupObservers()

        viewModel.readMeLiveData.observe(this, Observer {
            markdown_view.loadMarkdownFromUrl(it.downloadUrl)
        })
    }

}