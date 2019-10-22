package com.github.demo.ui.publicRepo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.demo.R
import com.github.demo.data.models.Repository
import com.github.demo.di.components.FragmentComponent
import com.github.demo.ui.base.BaseFragment
import com.github.demo.ui.details.RepoDetailsActivity
import com.github.demo.utils.common.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_public_repo.*
import javax.inject.Inject


class PublicRepoListFragment : BaseFragment<PublicRepoListViewModel>() {

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var adapter: PublicRepoAdapter
    private var repoList = ArrayList<Repository>()

    override fun getLayoutResource() = R.layout.fragment_public_repo

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun injectDependency(component: FragmentComponent) {
        component.inject(this)
    }

    override fun setAdapters() {
        super.setAdapters()

        val click: (Repository) -> Unit = {

            Intent(activity, RepoDetailsActivity::class.java)
                .run {
                    putExtra("REPO", it)
                    startActivity(this)
                }
        }

        adapter = PublicRepoAdapter(context!!, repoList, click)
        rvPublicRepos.setHasFixedSize(true)

        rvPublicRepos.layoutManager = linearLayoutManager
        rvPublicRepos.adapter = adapter

        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.loadNextPage()
            }
        }

        rvPublicRepos.addOnScrollListener(scrollListener)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.repoListLiveData.observe(this, Observer {
            repoList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

}