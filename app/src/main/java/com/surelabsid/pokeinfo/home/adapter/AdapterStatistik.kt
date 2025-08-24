package com.surelabsid.pokeinfo.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.core.data.source.network.model.StatisticsData
import com.surelabsid.pokeinfo.databinding.ItemAdapterStatistikBinding

class AdapterStatistik : RecyclerView.Adapter<AdapterStatistik.ViewHolder>() {

    private val listStatistics = mutableListOf<StatisticsData?>()

    inner class ViewHolder(private val binding: ItemAdapterStatistikBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(statisticsData: StatisticsData?) {
            binding.statisikName.text = statisticsData?.title
            binding.power.progress = statisticsData?.power?.toInt() ?: 0
            binding.progressText.text = statisticsData?.power.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterStatistik.ViewHolder {
        val binding =
            ItemAdapterStatistikBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterStatistik.ViewHolder, position: Int) {
        holder.onBind(listStatistics[position])
    }

    override fun getItemCount(): Int {
        return listStatistics.size
    }

    fun addData(data: List<StatisticsData?>?) {
        data?.let {
            listStatistics.clear()
            listStatistics.addAll(data)
            notifyDataSetChanged()
        }
    }

}