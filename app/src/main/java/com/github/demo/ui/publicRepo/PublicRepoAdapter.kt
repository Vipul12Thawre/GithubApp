package com.github.demo.ui.publicRepo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.github.demo.R
import com.github.demo.data.models.Repository
import com.github.demo.utils.common.GlideApp


class PublicRepoAdapter(
    private val context: Context,
    private val listRepos: List<Repository>,
    val click: (Repository) -> Unit

) : RecyclerView.Adapter<PublicRepoAdapter.PublicRepoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicRepoItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return PublicRepoItemViewHolder(view)
    }

    override fun getItemCount() = listRepos.size

    override fun onBindViewHolder(holder: PublicRepoItemViewHolder, position: Int) {
        holder.txtRepoName.text = listRepos[position].repoName
        holder.txtRepoDesc.text = listRepos[position].repoDesc ?: ""
        holder.txtDevID.text = listRepos[position].devName


        GlideApp.with(context)
            .load(listRepos[position].devAvatar).apply(RequestOptions.circleCropTransform())
            .into(holder.imgDev)
    }

    inner class PublicRepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgDev = itemView.findViewById<ImageView>(R.id.imgDev)
        val txtRepoName = itemView.findViewById<TextView>(R.id.txtRepoName)
        val txtRepoDesc = itemView.findViewById<TextView>(R.id.txtRepoDesc)
        val txtDevID = itemView.findViewById<TextView>(R.id.txtDevID)


        init {
            itemView.setOnClickListener {
                click(listRepos[adapterPosition])

            }
        }
    }
}