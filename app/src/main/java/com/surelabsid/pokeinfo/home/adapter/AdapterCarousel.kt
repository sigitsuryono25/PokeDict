package com.surelabsid.pokeinfo.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.surelabsid.pokeinfo.databinding.ItemAdapterCarouselBinding

class AdapterCarousel : RecyclerView.Adapter<AdapterCarousel.ViewHolder>() {
    private var imagesList = mutableListOf<String?>()
    var onImageClick: ((List<String?>) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemAdapterCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBindItem(url: String?) {

            Glide
                .with(binding.root)
                .load(url)
                .into(binding.image)
            binding.root.setOnClickListener {
                onImageClick?.invoke(imagesList)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemAdapterCarouselBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(imagesList[position])
    }

    override fun getItemCount(): Int = imagesList.size

    fun addData(data: List<String?>?) {
        data?.let {
            imagesList.clear()
            imagesList.addAll(data)
            notifyDataSetChanged()
        }
    }

}