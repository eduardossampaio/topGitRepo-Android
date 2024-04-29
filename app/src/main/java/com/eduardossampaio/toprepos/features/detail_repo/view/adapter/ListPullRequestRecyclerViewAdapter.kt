package com.eduardossampaio.toprepos.features.detail_repo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eduardossampaio.toprepos.databinding.PullrequestListItemBinding
import com.eduardossampaio.toprepos.utils.toString
import com.esampaio.core.models.PullRequest
import com.squareup.picasso.Picasso

class ListPullRequestRecyclerViewAdapter(private val context:Context): ListAdapter<PullRequest, ListPullRequestRecyclerViewAdapter.ListItemViewHolder>(
    COMPARATOR
) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var items: MutableList<PullRequest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = PullrequestListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(items[position]);
    }

    fun setItems(newItems:List<PullRequest>){
        val currentList = newItems.toMutableList()
        submitList(currentList)
        items = currentList
    }
    inner class ListItemViewHolder(private val views: PullrequestListItemBinding) : ViewHolder(views.root) {

        fun bind(item:PullRequest){
            views.userName.text = item.authorName
            views.prTitle.text = item.title
            views.prBody.text = item.body
            views.createdAt.text = item.date.toString("dd/MM/yyyy")

            Picasso.get().load(item.authorProfilePictureUrl).into(views.userProfile);

//            bindAccessibility(item)
        }

        private fun bindAccessibility(item:PullRequest){
            val r = context.resources
            //ordem trocada para facilitar a leitura
//            views.userName.contentDescription = r.getString(R.string.accessibility_repository_name,item.name)//"Repositório ${item.name}"
//            views.repoName.contentDescription = r.getString(R.string.accessibility_repository_created_by,item.authorName)//"Criado por ${item.authorName}"
//
//            views.starCount.contentDescription = r.getString(R.string.accessibility_repository_stars_count,item.starCount)
//            views.forkCount.contentDescription = r.getString(R.string.accessibility_repository_fork_count,item.forkCount)
        }
    }

}