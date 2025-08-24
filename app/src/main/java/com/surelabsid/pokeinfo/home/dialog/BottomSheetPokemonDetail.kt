package com.surelabsid.pokeinfo.home.dialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.utils.base.BaseBottomSheetDialogFragment
import com.surelabsid.pokeinfo.R
import com.surelabsid.pokeinfo.databinding.BottomSheetPokemonDetailBinding
import com.surelabsid.pokeinfo.databinding.ItemChipBinding
import com.surelabsid.pokeinfo.home.adapter.AdapterCarousel
import com.surelabsid.pokeinfo.home.adapter.AdapterStatistik

private const val POKE_DETAIL = "POKE_DETAIL"

class BottomSheetPokemonDetail : BaseBottomSheetDialogFragment() {
    private var _binding: BottomSheetPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private var pokeDetailResponse: PokeDetail? = null
    private var isBookmark: Boolean = false
    private lateinit var carousel: AdapterCarousel
    private lateinit var statistik: AdapterStatistik

    companion object {
        val TAG: String = BottomSheetPokemonDetail::class.java.simpleName

        fun newInstance(pokeDetailResponse: PokeDetail?): BottomSheetPokemonDetail {
            val args = Bundle()
            args.putParcelable(POKE_DETAIL, pokeDetailResponse)
            val fragment = BottomSheetPokemonDetail()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokeDetailResponse = it.getParcelable(POKE_DETAIL)
        }
    }

    override fun initView() {
        super.initView()
        binding.pokeId.text = "#${pokeDetailResponse?.pokeId?.padStart(3, '0')}"
        binding.berat.text = pokeDetailResponse?.weight?.div(10.0).toString() + " kg"
        binding.tinggi.text = pokeDetailResponse?.height?.div(10.0).toString() + " m"
        binding.pokemonName.text =
            pokeDetailResponse?.pokeName?.replaceFirstChar { it.uppercase() }.toString()
        mappingAbilities()
        mappingTypes()
        setHeaderBackground(
            binding.images,
            pokeDetailResponse?.types ?: emptyList(),
            requireContext()
        )
        setHeaderBackground(
            binding.pokemonName,
            pokeDetailResponse?.types ?: emptyList(),
            requireContext()
        )
        setHeaderBackground(
            binding.root,
            pokeDetailResponse?.types ?: emptyList(),
            requireContext()
        )
    }

    override fun setLayout(container: ViewGroup?): View {
        _binding = BottomSheetPokemonDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun initClick() {
        super.initClick()
        binding.close.setOnClickListener {
            dismiss()
        }
        binding.bookmark.setOnClickListener {
            if (!isBookmark) {
                binding.bookmark.setImageResource(R.drawable.ic_fav_filled)
                isBookmark = true
            } else {
                binding.bookmark.setImageResource(R.drawable.ic_fav)
                isBookmark = false
            }
        }
    }

    override fun initRecyclerView() {
        super.initRecyclerView()
        carousel = AdapterCarousel()
        carousel.addData(pokeDetailResponse?.images)
        binding.images.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.images.adapter = carousel
        val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.images)

        statistik = AdapterStatistik()
        statistik.addData(pokeDetailResponse?.statistics)
        binding.statRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.statRv.adapter = statistik
    }

    private fun mappingTypes() {
        pokeDetailResponse?.types?.forEach {
            val chipBinding = ItemChipBinding.inflate(layoutInflater, null, false)
            chipBinding.chip.text = it.toString()
            chipBinding.chip.isClickable = false
            binding.chipTypes.addView(chipBinding.root)
        }
    }

    private fun mappingAbilities() {
        pokeDetailResponse?.abilities?.forEach {
            val chipBinding = ItemChipBinding.inflate(layoutInflater, null, false)
            chipBinding.chip.text = it.toString()
            chipBinding.chip.isClickable = false
            binding.abilities.addView(chipBinding.root)
        }
    }

    private fun setHeaderBackground(view: View, types: List<String?>, context: Context) {
        val colors = when {
            types.size >= 2 -> intArrayOf(
                getTypeColor(context, types.get(0)),
                getTypeColor(context, types.get(1))
            )

            types.isNotEmpty() -> intArrayOf(
                getTypeColor(context, types.get(0)),
                getTypeColor(context, types.get(0))
            )

            else -> intArrayOf(
                ContextCompat.getColor(context, android.R.color.darker_gray),
                ContextCompat.getColor(context, android.R.color.darker_gray)
            )
        }

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            colors
        )
        view.background = gradient

        // Cek luminance warna rata-rata
        val avgColor = androidx.core.graphics.ColorUtils.blendARGB(colors[0], colors[1], 0.5f)
        val luminance = androidx.core.graphics.ColorUtils.calculateLuminance(avgColor)

        // Atur font color berdasarkan kecerahan
        if (view is android.widget.TextView) {
            if (luminance < 0.5) {
                view.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            } else {
                view.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }
        }
    }

    fun getTypeColor(context: Context, type: String?): Int {
        return when (type.toString().lowercase()) {
            "normal" -> "#A8A77A".toColorInt()
            "fire" -> "#EE8130".toColorInt()
            "water" -> "#6390F0".toColorInt()
            "electric" -> "#F7D02C".toColorInt()
            "grass" -> "#7AC74C".toColorInt()
            "ice" -> "#96D9D6".toColorInt()
            "fighting" -> "#C22E28".toColorInt()
            "poison" -> "#A33EA1".toColorInt()
            "ground" -> "#E2BF65".toColorInt()
            "flying" -> "#A98FF3".toColorInt()
            "psychic" -> "#F95587".toColorInt()
            "bug" -> "#A6B91A".toColorInt()
            "rock" -> "#B6A136".toColorInt()
            "ghost" -> "#735797".toColorInt()
            "dragon" -> "#6F35FC".toColorInt()
            "dark" -> "#705746".toColorInt()
            "steel" -> "#B7B7CE".toColorInt()
            "fairy" -> "#D685AD".toColorInt()
            else -> ContextCompat.getColor(context, android.R.color.darker_gray)
        }
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog ?: return
        val bottomSheet =
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        val behavior =
            com.google.android.material.bottomsheet.BottomSheetBehavior.from(bottomSheet as View)
        behavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }
}