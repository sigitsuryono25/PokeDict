package com.surelabsid.pokeinfo.home.dialog

import android.view.View
import android.view.ViewGroup
import com.surelabsid.core.utils.base.BaseBottomSheetDialogFragment
import com.surelabsid.pokeinfo.databinding.BottomSheetHelpBinding

class BottomSheetHelp : BaseBottomSheetDialogFragment() {
    private var _binding: BottomSheetHelpBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG: String = BottomSheetHelp::class.java.simpleName
    }

    override fun setLayout(container: ViewGroup?): View {
        _binding = BottomSheetHelpBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun initClick() {
        super.initClick()
        binding.tutup.setOnClickListener {
            dismiss()
        }
    }

}