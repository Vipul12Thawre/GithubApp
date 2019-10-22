package com.github.demo.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.demo.R
import com.github.demo.data.models.Repository
import com.github.demo.di.components.FragmentComponent
import com.github.demo.ui.base.BaseFragment
import com.github.demo.ui.details.RepoDetailsActivity
import com.github.demo.ui.publicRepo.PublicRepoAdapter
import com.github.demo.utils.common.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_public_repo.*
import kotlinx.android.synthetic.main.fragment_search_repo.*
import javax.inject.Inject

class SearchRepoFragment : BaseFragment<SearchRepoViewModel>() {

    override fun getLayoutResource() = R.layout.fragment_search_repo

    private lateinit var adapter: PublicRepoAdapter
    private var repoList = ArrayList<Repository>()
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun injectDependency(component: FragmentComponent) {
        component.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.repoListLiveData.observe(this, Observer {
            repoList.clear()
            repoList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }


    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchUsers(p0.toString())
            }

        })
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
        rvSearchedRepos.setHasFixedSize(true)

        rvSearchedRepos.layoutManager = linearLayoutManager
        rvSearchedRepos.adapter = adapter

    }
}