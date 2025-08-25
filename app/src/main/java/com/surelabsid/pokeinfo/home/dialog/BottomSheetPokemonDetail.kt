package com.surelabsid.pokeinfo.home.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.utils.base.BaseBottomSheetDialogFragment
import com.surelabsid.core.utils.helpers.FileDownloader
import com.surelabsid.core.utils.helpers.checkIfFragmentAttached
import com.surelabsid.core.utils.helpers.getTypeColor
import com.surelabsid.core.utils.helpers.hideView
import com.surelabsid.core.utils.helpers.showView
import com.surelabsid.pokeinfo.R
import com.surelabsid.pokeinfo.databinding.BottomSheetPokemonDetailBinding
import com.surelabsid.pokeinfo.databinding.ItemChipBinding
import com.surelabsid.pokeinfo.home.HomeViewModel
import com.surelabsid.pokeinfo.home.adapter.AdapterCarousel
import com.surelabsid.pokeinfo.home.adapter.AdapterStatistik
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

private const val POKE_DETAIL = "POKE_DETAIL"

class BottomSheetPokemonDetail : BaseBottomSheetDialogFragment() {
    private var _binding: BottomSheetPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private var pokeDetailResponse: PokeDetail? = null
    private lateinit var carousel: AdapterCarousel
    private lateinit var statistik: AdapterStatistik
    private val viewModel: HomeViewModel by activityViewModel()
    var onDismiss: (() -> Unit)? = null

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

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokeDetailResponse = it.getParcelable(POKE_DETAIL)
        }
    }

    override fun initView() {
        super.initView()
        binding.pokeId.text =
            activity?.getString(R.string.poke_id, pokeDetailResponse?.pokeId?.padStart(3, '0'))
        binding.berat.text =
            activity?.getString(
                R.string.weight_kg,
                pokeDetailResponse?.weight?.div(10.0).toString()
            )
        binding.tinggi.text =
            activity?.getString(R.string.height_m, pokeDetailResponse?.height?.div(10.0).toString())
        binding.pokemonName.text =
            pokeDetailResponse?.pokeName?.replaceFirstChar { it.uppercase() }.toString()
        mappingAbilities()
        mappingTypes()
        toggleStatistics()
        setupBackground()
    }

    override fun setLayout(container: ViewGroup?): View {
        _binding = BottomSheetPokemonDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    override fun initObserver() {
        super.initObserver()
        viewModel.favStatus.observe(this) {
            checkIfFragmentAttached {
                dismissDialog()
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                if (it.status) {
                    binding.bookmark.setImageResource(R.drawable.ic_fav_filled)
                } else {
                    binding.bookmark.setImageResource(R.drawable.ic_fav)
                }
            }
        }
        viewModel.isFavorite.observe(this) {
            checkIfFragmentAttached {
                if (it) {
                    binding.bookmark.setImageResource(R.drawable.ic_fav_filled)
                } else {
                    binding.bookmark.setImageResource(R.drawable.ic_fav)
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        viewModel.checkFavorite(pokeDetailResponse?.pokeId.toString())
    }

    override fun initClick() {
        super.initClick()
        binding.close.setOnClickListener {
            dismiss()
        }

        binding.bookmark.setOnClickListener {
            showDialog()
            viewModel.setToFavorite(pokeDetailResponse?.pokeId.toString())
        }

        binding.share.setOnClickListener {
            sharePokemon()
        }

        binding.help.setOnClickListener {
            showHelpDialog()
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }

    private fun sharePokemon() {
        showDialog()
        checkIfFragmentAttached {
            var stat = ""
            pokeDetailResponse?.statistics?.forEach {
                stat += "${it?.title} : ${it?.power}\n"
            }
            val image =
                if (pokeDetailResponse?.images?.isNotEmpty() == true) pokeDetailResponse?.images?.get(
                    0
                ) else ""
            val caption = """
Hey, check this out!.
Here is one of my favorite pokemon *${pokeDetailResponse?.pokeName?.replaceFirstChar { it.uppercase() }}*.

Pokemon Statistics:
$stat
        """.trimMargin()
            lifecycleScope.launch {
                FileDownloader.downloadImage(
                    this@checkIfFragmentAttached,
                    image.toString(),
                    "${pokeDetailResponse?.pokeName}.png"
                ) { uriImage, message ->
                    dismissDialog()
                    FileDownloader.shareToWhatsApp(
                        this@checkIfFragmentAttached,
                        uriImage,
                        caption
                    )
                }
            }
        }
    }

    private fun mappingTypes() {
        if (pokeDetailResponse?.types?.isEmpty() == true) {
            binding.noTypes.showView()
        } else {
            binding.noTypes.hideView()
            pokeDetailResponse?.types?.forEach {
                val chipBinding = ItemChipBinding.inflate(layoutInflater, null, false)
                chipBinding.chip.text = it.toString()
                chipBinding.chip.isClickable = false
                binding.chipTypes.addView(chipBinding.root)
            }
        }
    }

    private fun mappingAbilities() {
        if (pokeDetailResponse?.abilities?.isEmpty() == true) {
            binding.noAbilities.showView()
        } else {
            binding.noAbilities.hideView()
            pokeDetailResponse?.abilities?.forEach {
                val chipBinding = ItemChipBinding.inflate(layoutInflater, null, false)
                chipBinding.chip.text = it.toString()
                chipBinding.chip.isClickable = false
                binding.abilities.addView(chipBinding.root)
            }
        }
    }

    private fun setHeaderBackground(view: View, types: List<String?>, context: Context) {
        checkIfFragmentAttached {
            val colors = when {
                types.size >= 2 -> intArrayOf(
                    getTypeColor(types[0]),
                    getTypeColor(types[1])
                )

                types.isNotEmpty() -> intArrayOf(
                    getTypeColor(types[0]),
                    getTypeColor(types[0])
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

            val avgColor = androidx.core.graphics.ColorUtils.blendARGB(colors[0], colors[1], 0.5f)
            val luminance = androidx.core.graphics.ColorUtils.calculateLuminance(avgColor)

            if (view is android.widget.TextView) {
                if (luminance < 0.5) {
                    view.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                } else {
                    view.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                }
            }
        }
    }

    private fun toggleStatistics() {
        if (pokeDetailResponse?.statistics?.isEmpty() == true) {
            binding.noStat.showView()
            binding.statRv.hideView()
        } else {
            binding.noStat.hideView()
            binding.statRv.showView()
        }
    }

    private fun setupBackground() {
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

    private fun showHelpDialog() {
        val bottomSheetHelp = BottomSheetHelp()
        bottomSheetHelp.show(childFragmentManager, BottomSheetHelp.TAG)

    }
}