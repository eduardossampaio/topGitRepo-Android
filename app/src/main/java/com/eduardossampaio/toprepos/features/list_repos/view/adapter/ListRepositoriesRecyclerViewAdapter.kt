package com.eduardossampaio.toprepos.features.list_repos.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eduardossampaio.toprepos.R
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

            views.content.setOnClickListener {
                onItemClicked(item)
            }

            bindAccessibility(item)
        }

        private fun bindAccessibility(item:Repo){
            val r = context.resources
            //ordem trocada para facilitar a leitura
            views.userName.contentDescription = r.getString(R.string.accessibility_repository_name,item.name)//"Repositório ${item.name}"
            views.repoName.contentDescription = r.getString(R.string.accessibility_repository_created_by,item.authorName)//"Criado por ${item.authorName}"

            views.starCount.contentDescription = r.getString(R.string.accessibility_repository_stars_count,item.starCount)
            views.forkCount.contentDescription = r.getString(R.string.accessibility_repository_fork_count,item.forkCount)
        }
    }

}