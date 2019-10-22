package com.github.demo.ui.offlineRepos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.demo.R
import com.github.demo.data.models.Repository
import com.github.demo.di.components.FragmentComponent
import com.github.demo.ui.base.BaseFragment
import com.github.demo.ui.details.RepoDetailsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_offline_repo.*
import javax.inject.Inject


class OfflineReposFragment : BaseFragment<OfflineReposViewModel>(), OnItemClickListener {

    private lateinit var adapter: OfflineRepoAdapter
    private var repoList = ArrayList<Repository>()

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun getLayoutResource() = R.layout.fragment_offline_repo

    override fun injectDependency(component: FragmentComponent) {
        component.inject(this)
    }


    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        setAdapters()
    }

    override fun setAdapters() {
        super.setAdapters()

        adapter = OfflineRepoAdapter(context!!, repoList, this)
        rvOfflineList.setHasFixedSize(true)

        rvOfflineList.layoutManager = linearLayoutManager
        rvOfflineList.adapter = adapter
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.repoListLiveData.observe(this, Observer {
            repoList.clear()
            repoList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.deleteRepoLiveData.observe(this, Observer {
            Snackbar.make(offlineRoot, getString(it), Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun onItemClick(repository: Repository) {
        Intent(activity, RepoDetailsActivity::class.java)
            .run {
                putExtra("REPO", repository)
                startActivity(this)
            }
    }

    override fun onLongPress(repository: Repository) {
        val items = arrayOf<CharSequence>("Delete")

        val builder = AlertDialog.Builder(context!!)

        builder.setTitle("Options")
        builder.setItems(
            items
        ) { _, item ->
            when (item) {
                0 -> {
                    viewModel.deleteRepo(repository)
                }
            }
        }
        builder.show()
    }

}