package com.nedaluof.quotes.ui.inspirational

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.FragmentInspirationalBinding
import com.nedaluof.quotes.ui.base.BaseFragment
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by NedaluOf on 8/10/2021.
 */
@AndroidEntryPoint
class InspirationalFragment : BaseFragment<FragmentInspirationalBinding>() {
    override var layoutId = R.layout.fragment_inspirational
    override var bindingVariable = 0
    private val inspirationalViewModel by viewModels<InspirationalViewModel>()
    override fun getViewModel() = inspirationalViewModel
    private lateinit var inspirationalQuotesPagedAdapter: InspirationalQuotesPagedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        inspirationalQuotesPagedAdapter = InspirationalQuotesPagedAdapter(
            { authorSlug -> navigateToAuthorQuotes(authorSlug) },
            { tagName -> navigateToTagQuotes(tagName) })

        inspirationalQuotesPagedAdapter.apply {
            viewBinding.recyclerView.adapter = inspirationalQuotesPagedAdapter.withLoadStateFooter(
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
        with(inspirationalViewModel) {
            lifecycleScope.launch {
                //load quotes list
                loadInspirationalQuotes().collectLatest { data ->
                    inspirationalQuotesPagedAdapter.submitData(data)
                }
            }

        }
    }

    private fun navigateToAuthorQuotes(authorSlug: String) {
        val action =
            InspirationalFragmentDirections.actionNavigationInspirationalToAuthorQuotesFragment(
                authorSlug
            )
        findNavController().navigate(action)
    }

    private fun navigateToTagQuotes(tagName: String) {
        val action =
            InspirationalFragmentDirections.actionNavigationInspirationalToTagQuotesFragment(
                tagName
            )
        findNavController().navigate(action)
    }
}