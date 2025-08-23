package com.surelabsid.pokeinfo.home

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surelabsid.core.utils.base.BaseActivity
import com.surelabsid.pokeinfo.databinding.ActivityHomeBinding
import com.surelabsid.pokeinfo.home.adapter.AdapterPokeList
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var adapterPokeList: AdapterPokeList

    override fun setLayoutView(): View {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initAppBar() {
        super.initAppBar()
        binding.appBarLayout.toolbarMenu.visibility = View.GONE
        binding.appBarLayout.smallTitle.text = "Pokemon Glossarium"
    }

    override fun initView() {
        super.initView()
        setInsets(binding.root)
    }

    override fun initData() {
        super.initData()
        viewModel.getPokeList(0, 21)
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.pokeList.observe(this) {
            adapterPokeList.addList(it)
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
                    // Scroll ke bawah → hide
                    binding.search.hide()
                } else if (dy < 0) {
                    // Scroll ke atas → show
                    binding.search.show()
                }
            }
        })
    }
}