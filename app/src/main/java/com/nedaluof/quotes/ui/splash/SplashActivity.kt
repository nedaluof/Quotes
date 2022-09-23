package com.nedaluof.quotes.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.ActivitySplashBinding
import com.nedaluof.quotes.ui.base.BaseActivity
import com.nedaluof.quotes.ui.base.BaseViewModel
import com.nedaluof.quotes.ui.main.QuotesActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

  override val bindingVariable = 0
  override val layoutId = R.layout.activity_splash
  override fun getViewModel(): BaseViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(QuotesActivity.getIntent(this))
      finish()
    }, 1500)
  }
}