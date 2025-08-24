package com.surelabsid.pokeinfo.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.pokeinfo.databinding.ItemAdapterPokeListBinding
import timber.log.Timber

class AdapterPokeList : RecyclerView.Adapter<AdapterPokeList.ViewHolder>() {
    private val pokeList = mutableListOf<PokemonEntity?>()
    private val originalList = mutableListOf<PokemonEntity?>()
    var onItemClicked: ((PokemonEntity?) -> Unit)? = null

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

    fun addList(list: List<PokemonEntity?>?, clearFirst: Boolean = false) {
        if (clearFirst) {
            pokeList.clear()
            originalList.clear()
            pokeList.addAll(list.orEmpty())
            originalList.addAll(list.orEmpty())
            notifyDataSetChanged()
        } else {
            pokeList.addAll(list.orEmpty())
            originalList.addAll(list.orEmpty())
            notifyItemRangeInserted(pokeList.size, list?.size ?: 0)
        }
    }

    fun filter(query: String) {
        pokeList.clear()
        Timber.e("query: $query")
        if (query.isEmpty()) {
            pokeList.addAll(originalList)
        } else {
            val filtered = originalList.filter { it?.name?.contains(query, ignoreCase = true) == true }
            pokeList.addAll(filtered)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemAdapterPokeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(pokemonItem: PokemonEntity?) {
            Glide.with(binding.root.context)
                .load(pokemonItem?.cover)
                .into(binding.images)
            binding.urutan.text = "#${pokemonItem?.pokeId}"
            binding.pokemonName.text = pokemonItem?.name?.replaceFirstChar { it.uppercase() }
            binding.root.setOnClickListener {
                onItemClicked?.invoke(pokemonItem)
            }
        }
    }
}