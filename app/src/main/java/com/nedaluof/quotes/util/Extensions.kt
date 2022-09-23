package com.nedaluof.quotes.util

import android.view.View

/**
 * Created by NedaluOf on 8/10/2021.
 */
fun View.click(doBlock: () -> Unit) {
  setOnClickListener { doBlock() }
}