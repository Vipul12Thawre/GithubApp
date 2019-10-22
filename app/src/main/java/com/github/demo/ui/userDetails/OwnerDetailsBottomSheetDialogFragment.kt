package com.github.demo.ui.userDetails

import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.request.RequestOptions
import com.github.demo.R
import com.github.demo.data.remote.response.owner.OwnerDetailsResponse
import com.github.demo.di.components.BottomSheetDialogFragmentComponent
import com.github.demo.ui.base.BaseBottomSheetDialogFragment
import com.github.demo.utils.common.GlideApp
import kotlinx.android.synthetic.main.bottmsheet_owner_details.*

class OwnerDetailsBottomSheetDialogFragment : BaseBottomSheetDialogFragment<OwnDetailsViewModel>() {


    companion object {
        private const val OWNER = "OWNER"
        private const val AVATAR = "AVATAR"

        const val TAG = "OwnerDetailsBottomSheetDialogFragment"
        fun newInstance(owner: String, avatar: String): OwnerDetailsBottomSheetDialogFragment {

            val args = Bundle()
            args.run {
                putString(OWNER, owner)
                putString(AVATAR, avatar)
            }
            val fragment = OwnerDetailsBottomSheetDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResource(): Int = R.layout.bottmsheet_owner_details

    override fun injectDependency(component: BottomSheetDialogFragmentComponent) {
        component.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        collectExtras()
    }

    private fun collectExtras() {
        arguments?.run {
            getString(OWNER).let {
                it?.run { viewModel.getOwnerDetails(this) }
            }

            getString(AVATAR).let {
                it?.run { loadAvatar(this) }
            }
        }
    }

    private fun loadAvatar(url: String?) {
        url?.let {
            GlideApp.with(this)
                .load(it).apply(RequestOptions.circleCropTransform())
                .into(imgDev)
        }

    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.ownerLiveData.observe(this, Observer {
            it?.run {
                bindData(this)
            }
        })
    }

    private fun bindData(data: OwnerDetailsResponse) {
        txtFullName.text = data.name
        txtBio.text = data.bio
        txtFollowers.text = data.followers.toString()
        txtFollowing.text = data.following.toString()
        txtRepos.text = data.publicRepos.toString()
        loadAvatar(data.avatarUrl)
    }
}