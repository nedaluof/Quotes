package com.nedaluof.quotes.ui.anime.animequotes.characterquotes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.FragmentCharacterQuotesBinding
import com.nedaluof.quotes.ui.base.BaseFragment
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by NedaluOf on 8/12/2021.
 */
@AndroidEntryPoint
class CharacterQuotesFragment : BaseFragment<FragmentCharacterQuotesBinding>() {
    override var layoutId = R.layout.fragment_character_quotes
    override var bindingVariable = 0
    private val characterQuotesViewModel by viewModels<CharacterQuotesViewModel>()
    override fun getViewModel() = characterQuotesViewModel
    private lateinit var characterQuotesPagedAdapter: CharacterQuotesPagedAdapter
    private val safeArgs: CharacterQuotesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        characterQuotesPagedAdapter = CharacterQuotesPagedAdapter()
        characterQuotesPagedAdapter.apply {
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
        viewBinding.recyclerView.adapter = characterQuotesPagedAdapter.withLoadStateFooter(
            footer = LoadStateFooterAdapter { characterQuotesPagedAdapter.retry() }
        )
    }

    private fun observeViewModel() {
        val characterName = safeArgs.characterName
        with(characterQuotesViewModel) {
            lifecycleScope.launch {
                loadCharacterQuotes(characterName).collectLatest { data ->
                    characterQuotesPagedAdapter.submitData(data)
                }
            }
        }
    }

}