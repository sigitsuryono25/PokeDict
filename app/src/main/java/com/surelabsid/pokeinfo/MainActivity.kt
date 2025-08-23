package com.surelabsid.pokeinfo

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.surelabsid.core.utils.base.BaseActivity
import com.surelabsid.pokeinfo.databinding.ActivityMainBinding
import com.surelabsid.pokeinfo.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun setLayoutView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        super.initView()
        binding.versionApp.text = "v${BuildConfig.VERSION_NAME}"
        Glide.with(this)
            .load(R.drawable.pokemon_logo)
            .into(binding.images)
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}