package com.github.demo.ui.offlineRepos

import com.github.demo.data.models.Repository

interface OnItemClickListener {
    fun onItemClick(repository: Repository)
    fun onLongPress(repository: Repository)
}