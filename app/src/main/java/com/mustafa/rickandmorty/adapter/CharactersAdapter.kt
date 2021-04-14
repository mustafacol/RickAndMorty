package com.mustafa.rickandmorty.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafa.rickandmorty.R
import com.mustafa.rickandmorty.models.Character
import kotlinx.android.synthetic.main.character_cell.view.*

class CharactersAdapter() : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.character_cell,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(character.image).into(imageView)
            tvName.text = character.name
            tvStatus.text = character.status
            imageView.apply {
                borderWidth = 20f
                //borderColor = Color.BLACK
                borderColor = when (character.status) {
                    "Dead" -> Color.RED
                    "Alive" -> Color.GREEN
                    else -> Color.GRAY
                }
            }

            tvLastSeen.text = character.location.name

            setOnClickListener {
                onItemClickListener?.let { it(character) }
            }
        }
    }
    private var onItemClickListener: ((Character) -> Unit)? = null

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener
    }
}