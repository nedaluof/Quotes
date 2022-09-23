package com.nedaluof.quotes.ui.base

import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.nedaluof.data.datasource.local.PreferencesManager
import com.nedaluof.quotes.R
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

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

  @Inject
  lateinit var appRepository: PreferencesManager

  private var dayNightButton: ImageButton? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    handleNightMode()
    viewModel = getViewModel()
    //handling data binding in activity creation
    viewBinding = DataBindingUtil.setContentView(this, layoutId)
    viewBinding.apply {
      lifecycleOwner = this@BaseActivity
      setVariable(bindingVariable, viewModel ?: 0)
      executePendingBindings()
    }
  }

  fun toast(@StringRes stringId: Int) {
    Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show()
  }

  fun <T> StateFlow<T>.collectFlow(block: (T) -> Unit) {
    lifecycleScope.launch {
      this@collectFlow.collectLatest {
        block(it)
      }
    }
  }

  fun <T> LiveData<T>.observe(block: (T) -> Unit) {
    this.observe(this@BaseActivity, block)
  }

  fun setDayNightButton(dayNightButton: ImageButton) {
    this.dayNightButton = dayNightButton
    val enabled = appRepository.isNightModeEnabled()
    dayNightButton.setImageDrawable(
      ContextCompat.getDrawable(
        this, if (enabled) R.drawable.ic_night else R.drawable.ic_sun
      )
    )
  }

  fun changeDayNightMode() {
    val currentMode = appRepository.isNightModeEnabled()
    val newMode = !currentMode
    appRepository.setNightModeEnabled(newMode)
    handleNightMode()
  }

  private fun handleNightMode() {
    val enabled = appRepository.isNightModeEnabled()
    val mode = if (enabled) {
      AppCompatDelegate.MODE_NIGHT_YES
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
      } else {
        AppCompatDelegate.MODE_NIGHT_NO
      }
    }
    dayNightButton?.setImageDrawable(
      ContextCompat.getDrawable(
        this, if (enabled) R.drawable.ic_night else R.drawable.ic_sun
      )
    )
    AppCompatDelegate.setDefaultNightMode(mode)
  }
}