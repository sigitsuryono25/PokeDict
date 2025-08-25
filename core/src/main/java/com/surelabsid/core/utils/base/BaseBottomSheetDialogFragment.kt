package com.surelabsid.core.utils.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Suppress("DEPRECATION")
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {
    protected var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return setLayout(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initProgressDialog()
        initData()
        initClick()
        initRecyclerView()
        initObserver()
    }

    abstract fun setLayout(container: ViewGroup?): View

    open fun initView() {}
    open fun initClick() {}
    open fun initRecyclerView() {}

    open fun initObserver() {}

    open fun initData(){}

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setMessage("Sedang memproses...")
        progressDialog?.isIndeterminate = true
        progressDialog?.setCancelable(false)
    }

    fun showDialog() {
        progressDialog?.show()
    }

    fun dismissDialog() {
        if (progressDialog?.isShowing == true) progressDialog?.dismiss()
    }
}