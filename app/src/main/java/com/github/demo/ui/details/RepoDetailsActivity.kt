package com.github.demo.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.request.RequestOptions
import com.github.demo.R
import com.github.demo.data.models.Repository
import com.github.demo.di.components.ActivityComponent
import com.github.demo.ui.base.BaseActivity
import com.github.demo.ui.contributors.ContributorsListFragment
import com.github.demo.ui.publicRepo.PublicRepoListFragment
import com.github.demo.ui.readMe.ReadMeFragment
import com.github.demo.ui.userDetails.OwnerDetailsBottomSheetDialogFragment
import com.github.demo.utils.common.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_repo_details.*

class RepoDetailsActivity : BaseActivity<RepoDetailsViewModel>() {

    private lateinit var repo: Repository

    override fun getLayoutResource() = R.layout.activity_repo_details

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)

        collectExtras()
        setRepoDetails()
        setupViewPager(pager)
        tab_layout.setupWithViewPager(pager)
        registerEvents()
    }

    private fun registerEvents() {
        txtDevID.setOnClickListener {
            val modalBottomSheet =
                OwnerDetailsBottomSheetDialogFragment.newInstance(repo.devName, repo.devAvatar)
            modalBottomSheet.show(supportFragmentManager, OwnerDetailsBottomSheetDialogFragment.TAG)
        }
    }

    private fun setRepoDetails() {
        repo.apply {
            txtRepoDesc.text = repoDesc
            txtDevID.text = devName
            txtRepoName.text = repoName

            GlideApp.with(this@RepoDetailsActivity)
                .load(repo.devAvatar).apply(RequestOptions.circleCropTransform())
                .into(imgDev)

            viewModel.saveToDb(this)
        }
    }

    private fun collectExtras() {
        intent.extras?.run {
            repo = intent.getParcelableExtra("REPO") as Repository
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = DetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(
            ReadMeFragment.newInstance(repo.devName, repo.repoName),
            "Info"
        )
        adapter.addFragment(
            ContributorsListFragment.newInstance(repo.devName, repo.repoName),
            "Contributors"
        )

        viewPager.adapter = adapter
    }

    internal inner class DetailsViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList.get(position)
        }
    }
}
