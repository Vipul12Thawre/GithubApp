package com.github.demo.ui.contributors

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.demo.R
import com.github.demo.data.models.Contributor
import com.github.demo.di.components.FragmentComponent
import com.github.demo.ui.base.BaseFragment
import com.github.demo.ui.details.RepoDetailsActivity
import com.github.demo.ui.userDetails.OwnerDetailsBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_contributors_list.*
import javax.inject.Inject

class ContributorsListFragment : BaseFragment<ContributorsViewModel>() {

    private lateinit var sheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var adapter: ContributorsListAdapter

    private var repoList = ArrayList<Contributor>()

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        private const val OWNER = "OWNER"
        private const val REPO_NAME = "REPO_NAME"

        fun newInstance(owner: String, repoName: String): ContributorsListFragment {

            val args = Bundle()
            args.run {
                putString(OWNER, owner)
                putString(REPO_NAME, repoName)
            }
            val fragment = ContributorsListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun getLayoutResource() = R.layout.fragment_contributors_list

    override fun injectDependency(component: FragmentComponent) {
        component.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        arguments?.run {
            viewModel.getContributors(getString(OWNER)!!, getString(REPO_NAME)!!)
        }
    }

    override fun setAdapters() {
        super.setAdapters()

        val click: (Contributor) -> Unit = {

            val modalBottomSheet =
                OwnerDetailsBottomSheetDialogFragment.newInstance(it.contributor, it.avatar)
            modalBottomSheet.show(fragmentManager!!, OwnerDetailsBottomSheetDialogFragment.TAG)

        }

        adapter = ContributorsListAdapter(context!!, repoList, click)
        rvContributorsList.setHasFixedSize(true)

        rvContributorsList.layoutManager = linearLayoutManager
        rvContributorsList.adapter = adapter
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.constributorsLiveData.observe(this, Observer {
            repoList.clear()
            repoList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

}