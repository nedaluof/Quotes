package com.nedaluof.quotes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nedaluof.quotes.R
import com.nedaluof.quotes.util.initBottomSheetBehavior

/**
 * Created by NedaluOf on 8/10/2021.
 */
abstract class BaseBottomSheet<B : ViewDataBinding> : BottomSheetDialogFragment() {

  lateinit var viewBinding: B
  private lateinit var viewModel: ViewModel

  @get:LayoutRes
  abstract var layoutId: Int
  abstract var bindingVariable: Int
  abstract fun getViewModel(): ViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(false)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

    initBottomSheetBehavior { state ->
      when (state) {
        BottomSheetBehavior.STATE_HIDDEN -> dismiss()
       // BottomSheetBehavior.STATE_COLLAPSED -> dismiss()
      }
    }
    return viewBinding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = getViewModel()
    viewBinding.apply {
      setVariable(bindingVariable, viewModel)
      lifecycleOwner = viewLifecycleOwner
      executePendingBindings()
    }
  }

  fun show(manager: FragmentManager) {
    this.show(manager, null)
  }

  override fun getTheme() = R.style.AppBottomSheetDialogTheme

}