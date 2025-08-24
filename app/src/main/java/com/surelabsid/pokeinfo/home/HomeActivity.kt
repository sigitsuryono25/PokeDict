package com.surelabsid.pokeinfo.home

import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.core.data.common.CommonPrefs
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.utils.base.BaseActivity
import com.surelabsid.core.utils.helpers.hideView
import com.surelabsid.core.utils.helpers.showKeyboard
import com.surelabsid.core.utils.helpers.showView
import com.surelabsid.pokeinfo.databinding.ActivityHomeBinding
import com.surelabsid.pokeinfo.home.adapter.AdapterPokeList
import com.surelabsid.pokeinfo.home.dialog.BottomSheetPokemonDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var adapterPokeList: AdapterPokeList
    private var initOffset = 0

    override fun setLayoutView(): View {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initAppBar() {
        super.initAppBar()
        binding.appBarLayout.smallTitle.text = "Pokemon Glossarium"
    }

    override fun initView() {
        super.initView()
        onBackPressedDispatcher.addCallback {
            initBackPressed()
        }
        setInsets(binding.root)
    }


    override fun initData() {
        super.initData()
        viewModel.getPokeList(initOffset, 20)
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.pokeList.observe(this) {
            adapterPokeList.addList(it, initOffset == 0)
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

    override fun initOnClick() {
        super.initOnClick()
        binding.rvPokeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    binding.search.hide()
                } else if (dy < 0) {
                    binding.search.show()
                }

                // Disable pagination saat mode search aktif
                if (binding.search.tag != "hide") {
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        initOffset += 20
                        viewModel.getPokeList(initOffset, 20)
                    }
                }
            }
        })

        binding.search.setOnClickListener {
            if (binding.search.tag == "") {
                binding.appBarLayout.idSearchContainer.showView()
                binding.appBarLayout.smallTitle.hideView()
                binding.search.tag = "hide"
                binding.appBarLayout.idSearchContainer.requestLayout()
                binding.appBarLayout.idSearchContainer.showKeyboard()
            } else {
                binding.appBarLayout.idSearchContainer.hideView()
                binding.appBarLayout.smallTitle.showView()
                binding.appBarLayout.idSearchContainer.hideView()
                binding.search.tag = ""
            }
        }

        binding.appBarLayout.query.addTextChangedListener {
            if (binding.appBarLayout.query.text.toString().length > 3) {
                val query = binding.appBarLayout.query.text.toString()
                adapterPokeList.filter(query)
            } else adapterPokeList.filter("")
            true
        }

    }

    override fun initBackPressed() {
        if (binding.appBarLayout.idSearchContainer.isVisible) {
            binding.appBarLayout.idSearchContainer.hideView()
            binding.appBarLayout.smallTitle.showView()
            binding.search.tag = ""
            binding.appBarLayout.query.setText("")
            adapterPokeList.filter("")
        } else {
            super.initBackPressed()
        }
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
        pokeDetail.show(supportFragmentManager, BottomSheetPokemonDetail.TAG)
    }
}