package com.eduardossampaio.toprepos.views.list_repositories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eduardossampaio.toprepos.R
import com.esampaio.core.models.Repo
import com.squareup.picasso.Picasso

class ListRepositoriesRecyclerViewAdapter(private val context:Context): ListAdapter<Repo,ListRepositoriesRecyclerViewAdapter.ListItemViewHolder>(COMPARATOR) {

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
       val view = LayoutInflater.from(context).inflate(R.layout.repository_list_item,parent,false);
        return ListItemViewHolder(view);
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

    inner class ListItemViewHolder(itemView: View) : ViewHolder(itemView) {

        private val userImage:ImageView = itemView.findViewById(R.id.userProfile)
        private val userName:TextView = itemView.findViewById(R.id.userName)
        private val repoName:TextView = itemView.findViewById(R.id.repoName)
        private val starCount:TextView = itemView.findViewById(R.id.star_count)
        private val forkCount:TextView = itemView.findViewById(R.id.fork_count)

        fun bind(item:Repo){
            userName.text = item.authorName
            repoName.text = item.name
            starCount.text = item.starCount.toString()
            forkCount.text = item.forkCount.toString()

            Picasso.get().load(item.authorProfilePictureUrl).into(userImage);
        }
    }

}