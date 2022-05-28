@file:Suppress("UNCHECKED_CAST")

package com.nedaluof.quotes.util.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.nedaluof.quotes.ui.base.BaseRecycler

/**
 * Created by NedaluOf on 8/10/2021.
 */
object ViewsBindingAdapters {
    /**
     * Used With base recycler view adapter
     * */
    @BindingAdapter("items")
    @JvmStatic
    fun <Any> addItems(recyclerView: RecyclerView?, items: List<Any>?) {
        val adapter = recyclerView?.adapter as? BaseRecycler<Any>
        adapter?.clearItems()
        items?.let { adapter?.addItems(it) }
    }

    /**
     * used to generate/add chips
     * */
    @BindingAdapter(value = ["app:tags", "app:callback"])
    @JvmStatic
    fun generateChips(group: ChipGroup, tags: List<String>, callback: ChipsClick?) {
        for (index in tags.indices) {
            val context = group.context
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
            group.addView(chip)
        }
    }
}