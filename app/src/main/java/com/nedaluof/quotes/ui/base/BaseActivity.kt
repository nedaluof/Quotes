package com.nedaluof.quotes.ui.base

import android.os.Bundle
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by NedaluOf on 8/10/2021.
 */
abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var viewBinding: DB
    private var viewModel: BaseViewModel? = null
    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int
    abstract fun getViewModel(): BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        //handling data binding in activity creation
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(bindingVariable, viewModel ?: 0)
            executePendingBindings()
        }
    }
}