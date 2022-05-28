package com.nedaluof.quotes.ui.inspirational.tagquotes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.FragmentTagQuotesBinding
import com.nedaluof.quotes.ui.base.BaseFragment
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by NedaluOf on 8/10/2021.
 */
@AndroidEntryPoint
class TagQuotesFragment : BaseFragment<FragmentTagQuotesBinding>() {
    override var layoutId = R.layout.fragment_tag_quotes
    override var bindingVariable = 0
    private val tagViewModel by viewModels<TagQuotesViewModel>()
    override fun getViewModel() = tagViewModel
    private lateinit var tagQuotesPagedAdapter: TagQuotesPagedAdapter
    private val safeArgs by navArgs<TagQuotesFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        tagQuotesPagedAdapter =
            TagQuotesPagedAdapter { authorSlug -> navigateToAuthorQuotes(authorSlug) }

        tagQuotesPagedAdapter.apply {
            viewBinding.recyclerView.adapter = tagQuotesPagedAdapter.withLoadStateFooter(
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
        val tag = safeArgs.tag
        with(tagViewModel) {
            lifecycleScope.launch {
                //load quotes list
                loadInspirationalTagQuotes(tag).collectLatest { data ->
                    tagQuotesPagedAdapter.submitData(data)
                }
            }

        }
    }

    private fun navigateToAuthorQuotes(authorSlug: String) {
        val action =
            TagQuotesFragmentDirections.actionTagQuotesFragmentToAuthorQuotesFragment(
                authorSlug
            )
        findNavController().navigate(action)
    }
}