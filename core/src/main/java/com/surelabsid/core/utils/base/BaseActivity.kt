package com.surelabsid.core.utils.base

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseActivity : AppCompatActivity() {
    private var internetDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(setLayoutView())
        handleIntentData()
        initAppBar()
        initView()
        initData()
        initObserver()
        initOnClick()
        initRecyclerView()
    }

    /**
     * Init app bar. Use this function to initialize app bar.
     */
    open fun initAppBar() {}

    /**
     * Set layout view
     * @return View
     */
    abstract fun setLayoutView(): View

    /**
     * Init extas data from incoming intent.
     * Use this function to initialize extras data from incoming intent.
     */
    open fun handleIntentData() {}

    /**
     * Init data. Use this function to initialize data.
     * Like get from network, shared preference, etc.
     */
    open fun initData() {}

    /**
     * Init view. Use this function to initialize view.
     * Like set a static text view, button, etc.
     */
    open fun initView() {}

    /**
     * Init on click listener. Use this function to initialize on click listener.
     */
    open fun initOnClick() {}

    /**
     * Init observer. Use this function to initialize observer.
     * Put all observable of live data in here.
     */
    open fun initObserver() {}

    /**
     * Init back pressed. Use this function to initialize back pressed.
     * When need custom back pressed, override this function.
     */
    open fun initBackPressed() {
        finish()
    }

    /**
     * Init recycler view. Use this function to initialize recycler view.
     * Like set adapter, layout manager, etc.
     */
    open fun initRecyclerView() {}

    /**
     * Connection monitoring. Use this function to initialize connection monitoring.
     */
    open fun connectionMonitoring(isAvailable: Boolean) {}

    protected fun setInsets(root: View) {
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        setupNetworkListener()
    }

    override fun onPause() {
        super.onPause()
        internetDisposable?.dispose()
    }

    protected fun setupNetworkListener() {
        internetDisposable = ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isConnectedToInternet -> connectionMonitoring(isConnectedToInternet) })
    }
}