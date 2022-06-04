package com.nedaluof.quotes.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.ActivitySplashBinding
import com.nedaluof.quotes.ui.base.BaseActivity
import com.nedaluof.quotes.ui.main.QuotesActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

  override val bindingVariable = 0
  override val layoutId = R.layout.activity_splash
  override fun getViewModel(): ViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(QuotesActivity.getIntent(this))
      finish()
    }, 1500)
  }
}