@file:Suppress("UNCHECKED_CAST")

package com.nedaluof.quotes.util.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * Created by NedaluOf on 8/10/2021.
 */
object ViewsBindingAdapters {

    /**
     * used to generate/add chips
     * */
    @BindingAdapter(value = ["app:tags", "app:callback"])
    @JvmStatic
    fun ChipGroup.generateChips(tags: List<String>, callback: ChipsClick?) {
        for (index in tags.indices) {
            val tagName = tags[index]
            val chip = Chip(context)
            /* val paddingDp = TypedValue.applyDimension(
                 TypedValue.COMPLEX_UNIT_DIP, 10f,
                 context.resources.displayMetrics
             ).toInt()
             chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)*/
            chip.text = tagName
            //chip.setCloseIconResource(R.drawable.ic_action_navigation_close);
            //chip.setCloseIconVisible(true);
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnClickListener {
                callback?.onChipClick(tags[index])
            }
            /* chip.setOnCloseIconClickListener { v: View ->
                 tags.remove(tagName)
                 chipGroup.removeView(chip)
             }*/
            addView(chip)
        }
    }
}