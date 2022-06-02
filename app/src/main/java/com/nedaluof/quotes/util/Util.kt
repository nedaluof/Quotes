package com.nedaluof.quotes.util

import android.graphics.Color
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.floor

/**
 * Created by NedaluOf on 8/10/2021.
 */
fun View.click(doBlock: () -> Unit) {
  setOnClickListener { doBlock() }
}

fun generateRandomColor(): IntArray {
  val rgb = 0xff + 1
  val colors = IntArray(2)
  val r1 = floor(Math.random() * rgb).toInt()
  val r2 = floor(Math.random() * rgb).toInt()
  val r3 = floor(Math.random() * rgb).toInt()
  colors[0] = Color.rgb(r1, r2, r3)
  if (r1 + r2 + r3 > 450) {
    colors[1] = Color.parseColor("#222222")
  } else {
    colors[1] = Color.parseColor("#ffffff")
  }
  return colors
}