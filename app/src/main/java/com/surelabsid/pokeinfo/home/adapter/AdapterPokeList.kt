package com.surelabsid.pokeinfo.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.core.data.source.network.model.PokemonItem
import com.surelabsid.pokeinfo.databinding.ItemAdapterPokeListBinding

class AdapterPokeList : RecyclerView.Adapter<AdapterPokeList.ViewHolder>() {
    private val pokeList = mutableListOf<PokemonItem?>()
    var onItemClicked: ((PokemonItem?) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemAdapterPokeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.onBind(pokeList[position])
    }

    override fun getItemCount(): Int = pokeList.size

    fun addList(list: List<PokemonItem?>?) {
        pokeList.clear()
        pokeList.addAll(list.orEmpty())
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemAdapterPokeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(pokemonItem: PokemonItem?) {
            Glide.with(binding.root.context)
                .load(pokemonItem?.imageUrl)
                .into(binding.images)
            binding.urutan.text = "#${pokemonItem?.id}"
            binding.pokemonName.text = pokemonItem?.name?.replaceFirstChar { it.uppercase() }
            binding.root.setOnClickListener {
                onItemClicked?.invoke(pokemonItem)
            }
        }
    }
}