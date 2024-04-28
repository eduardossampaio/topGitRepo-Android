package com.eduardossampaio.toprepos.features.list_repos.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eduardossampaio.toprepos.databinding.RepositoryListItemBinding
import com.esampaio.core.models.Repo
import com.squareup.picasso.Picasso

class ListRepositoriesRecyclerViewAdapter(private val context:Context, private val onItemSelected: (Repo) -> Unit): ListAdapter<Repo, ListRepositoriesRecyclerViewAdapter.ListItemViewHolder>(
    COMPARATOR
) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var items: MutableList<Repo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = RepositoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding){
            onItemSelected(it)
        }
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(items[position]);
    }

    fun appendItems(newItems:List<Repo>){
        val currentList = items.toMutableList()
        currentList.addAll(newItems);
        submitList(currentList)
        items = currentList
    }

    inner class ListItemViewHolder(private val views: RepositoryListItemBinding, private val onItemClicked: (Repo) -> Unit) : ViewHolder(views.root) {

        fun bind(item:Repo){
            views.userName.text = item.authorName
            views.repoName.text = item.name
            views.repoDescription.text = item.description
            views.starCount.text = item.starCount.toString()
            views.forkCount.text = item.forkCount.toString()

            Picasso.get().load(item.authorProfilePictureUrl).into(views.userProfile);

            itemView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

}