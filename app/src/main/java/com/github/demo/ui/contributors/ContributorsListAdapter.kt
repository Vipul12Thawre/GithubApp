package com.github.demo.ui.contributors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.github.demo.R
import com.github.demo.data.models.Contributor
import com.github.demo.utils.common.GlideApp


class ContributorsListAdapter(
    private val context: Context,
    private val listContributor: List<Contributor>,
    val click: (Contributor) -> Unit

) : RecyclerView.Adapter<ContributorsListAdapter.ContributorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contributor, parent, false)
        return ContributorHolder(view)
    }

    override fun getItemCount() = listContributor.size

    override fun onBindViewHolder(holder: ContributorHolder, position: Int) {
        holder.txtDevID.text = listContributor[position].contributor

        GlideApp.with(context)
            .load(listContributor[position].avatar).apply(RequestOptions.circleCropTransform())
            .into(holder.imgDev)
    }

    inner class ContributorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgDev = itemView.findViewById<ImageView>(R.id.imgDev)
        val txtDevID = itemView.findViewById<TextView>(R.id.txtDevId)

        init {
            itemView.setOnClickListener {
                click(listContributor[adapterPosition])

            }
        }
    }
}