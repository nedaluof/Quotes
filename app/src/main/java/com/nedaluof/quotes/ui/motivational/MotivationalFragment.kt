package com.nedaluof.quotes.ui.motivational

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nedaluof.quotes.R

class MotivationalFragment : Fragment() {

    private lateinit var motivationalViewModel: MotivationalViewModel
    private val indices = intArrayOf(1, 5, 3, 2, 4, 6)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        motivationalViewModel =
            ViewModelProvider(this).get(MotivationalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_motivational, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        motivationalViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val linear = root.findViewById<LinearLayout>(R.id.lyt)
        indices.forEach {
            linear.addView(createViews(it))
        }
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun createViews(i: Int): TextView {
        val root = layoutInflater.inflate(R.layout.item_test, null) as TextView
        root.text = "Nedal $i"
        return root
    }
}