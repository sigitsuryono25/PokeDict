package com.surelabsid.pokeinfo.home.dialog

import android.view.View
import android.view.ViewGroup
import com.surelabsid.core.utils.base.BaseBottomSheetDialogFragment
import com.surelabsid.pokeinfo.databinding.BottomSheetTentangAppBinding

class BottomSheetTentangApp : BaseBottomSheetDialogFragment() {
    private var _binding: BottomSheetTentangAppBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG: String = BottomSheetTentangApp::class.java.simpleName
    }

    override fun setLayout(container: ViewGroup?): View {
        _binding = BottomSheetTentangAppBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initClick() {
        super.initClick()
        binding.tutup.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}