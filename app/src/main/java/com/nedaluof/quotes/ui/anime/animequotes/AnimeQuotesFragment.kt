package com.nedaluof.quotes.ui.anime.animequotes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.FragmentAnimeQuotesBinding
import com.nedaluof.quotes.ui.base.BaseFragment
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by NedaluOf on 8/11/2021.
 */
@AndroidEntryPoint
class AnimeQuotesFragment : BaseFragment<FragmentAnimeQuotesBinding>() {
    override var layoutId = R.layout.fragment_anime_quotes
    override var bindingVariable = 0
    private val animeQuotesViewModel by viewModels<AnimeQuotesViewModel>()
    override fun getViewModel() = animeQuotesViewModel
    private lateinit var animeQuotesPagedAdapter: AnimeQuotesPagedAdapter
    private val safeArgs: AnimeQuotesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        animeQuotesPagedAdapter = AnimeQuotesPagedAdapter { characterName ->
            val action =
                AnimeQuotesFragmentDirections.actionAnimeQuotesFragmentToCharacterQuotesFragment(
                    characterName
                )
            findNavController().navigate(action)
        }

        animeQuotesPagedAdapter.apply {
            viewBinding.recyclerView.adapter = animeQuotesPagedAdapter.withLoadStateFooter(
                footer = LoadStateFooterAdapter { this.retry() }
            )
            addLoadStateListener { loadState ->
                viewBinding.message.isVisible = false
                if (loadState.refresh is LoadState.Loading) {
                    //startShimmer()
                    viewBinding.progress.visibility = View.VISIBLE
                } else {
                    //stopShimmer()
                    viewBinding.progress.visibility = View.GONE
                    viewBinding.message.isVisible = snapshot().isEmpty()
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        if (snapshot().isEmpty()) {
                            viewBinding.message.isVisible = true
                            viewBinding.message.text = error.error.message.toString()
                        }
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        val animeName = safeArgs.animeName
        with(animeQuotesViewModel) {
            lifecycleScope.launch {
                loadAnimeQuotes(animeName).collectLatest { data ->
                    animeQuotesPagedAdapter.submitData(data)
                }
            }
        }
    }

}