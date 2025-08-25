package com.surelabsid.pokeinfo.favorite

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.surelabsid.core.data.common.CommonPrefs
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.utils.base.BaseActivity
import com.surelabsid.core.utils.helpers.hideView
import com.surelabsid.core.utils.helpers.showView
import com.surelabsid.pokeinfo.databinding.ActivityFavoriteBinding
import com.surelabsid.pokeinfo.home.HomeViewModel
import com.surelabsid.pokeinfo.home.adapter.AdapterPokeList
import com.surelabsid.pokeinfo.home.dialog.BottomSheetPokemonDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : BaseActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var adapterPokeList: AdapterPokeList

    override fun setLayoutView(): View {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initData() {
        super.initData()
        favoriteViewModel.getPokemonFavorite()
    }

    override fun initView() {
        super.initView()
        setInsets(binding.root)
    }

    override fun initAppBar() {
        super.initAppBar()
        binding.appBarLayout.smallTitle.text = "My Favorite Pokemon"
        binding.appBarLayout.btnMore2.hideView()
        binding.appBarLayout.btnAppBarBack.setOnClickListener {
            finish()
        }
        binding.appBarLayout.btnMore.hideView()
    }

    override fun initObserver() {
        super.initObserver()
        favoriteViewModel.pokemonFav.observe(this) {
            if (it.isEmpty()) {
                binding.emptyLayout.root.showView()
                binding.frameLayout.hideView()
            } else {
                binding.emptyLayout.root.hideView()
                binding.frameLayout.showView()
                adapterPokeList.addList(it, true)
            }
        }
        viewModel.pokeDetail.observe(this) {
            if (it != null) {
                showPokeDetail(it)
            }
        }
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        adapterPokeList = AdapterPokeList()
        adapterPokeList.onItemClicked = {
            viewModel.getPokeDetail(it?.id.toString())
        }
        binding.rvPokeList.layoutManager = GridLayoutManager(this, 3)
        binding.rvPokeList.adapter = adapterPokeList
    }

    override fun connectionMonitoring(isAvailable: Boolean) {
        super.connectionMonitoring(isAvailable)
        binding.appBarLayout.lastSync.text = "Terakhir disinkronkan " + CommonPrefs.lastSync
        if (isAvailable) {
            binding.appBarLayout.offlineBanner.hideView()
        } else {
            binding.appBarLayout.offlineBanner.showView()
        }
    }

    private fun showPokeDetail(pokeDetailResponse: PokeDetail?) {
        val pokeDetail = BottomSheetPokemonDetail.newInstance(pokeDetailResponse)
        pokeDetail.onDismiss = {
            favoriteViewModel.getPokemonFavorite()
        }
        pokeDetail.show(supportFragmentManager, BottomSheetPokemonDetail.TAG)
    }
}