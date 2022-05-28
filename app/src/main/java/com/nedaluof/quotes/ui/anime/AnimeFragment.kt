package com.nedaluof.quotes.ui.anime

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nedaluof.quotes.BR
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.FragmentAnimeBinding
import com.nedaluof.quotes.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by NedaluOf on 8/10/2021.
 */
@AndroidEntryPoint
class AnimeFragment : BaseFragment<FragmentAnimeBinding>() {

    override var layoutId = R.layout.fragment_anime
    override var bindingVariable = BR.viewmodel
    private val animeViewModel by viewModels<AnimeViewModel>()
    override fun getViewModel() = animeViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnimeNameList()
    }

    private fun initAnimeNameList() {
        viewBinding.animeNameRecycler.apply {
            adapter = AnimeNameAdapter { animeName ->
                val action =
                    AnimeFragmentDirections.actionNavigationAnimeToAnimeQuotesFragment(animeName)
                findNavController().navigate(action)
            }
            setHasFixedSize(true)
        }
    }
}