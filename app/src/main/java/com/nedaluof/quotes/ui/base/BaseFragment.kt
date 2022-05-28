package com.nedaluof.quotes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by NedaluOf on 8/10/2021.
 */

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    lateinit var viewBinding: B
    private lateinit var viewModel: BaseViewModel

    @get:LayoutRes
    abstract var layoutId: Int
    abstract var bindingVariable: Int
    abstract fun getViewModel(): BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(false)
        viewModel = getViewModel()
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.run {
            lifecycleOwner = viewLifecycleOwner
            setVariable(bindingVariable, viewModel)
            executePendingBindings()
        }
        return viewBinding.root
    }

    fun toast(string : String){
        Toast.makeText(requireActivity(), string, Toast.LENGTH_LONG).show()
    }
}