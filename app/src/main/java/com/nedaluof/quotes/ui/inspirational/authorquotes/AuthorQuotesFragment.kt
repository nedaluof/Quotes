package com.nedaluof.quotes.ui.inspirational.authorquotes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.FragmentAuthorQuotesBinding
import com.nedaluof.quotes.ui.base.BaseFragment
import com.nedaluof.quotes.ui.base.LoadStateFooterAdapter
import com.nedaluof.quotes.ui.inspirational.InspirationalFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by NedaluOf on 8/10/2021.
 */
@AndroidEntryPoint
class AuthorQuotesFragment : BaseFragment<FragmentAuthorQuotesBinding>() {
    override var layoutId = R.layout.fragment_author_quotes
    override var bindingVariable = 0
    private val authorViewModel by viewModels<AuthorQuotesViewModel>()
    override fun getViewModel() = authorViewModel
    private lateinit var authorQuotesPagedAdapter: AuthorQuotesPagedAdapter
    private val safeArgs by navArgs<AuthorQuotesFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        authorQuotesPagedAdapter =
            AuthorQuotesPagedAdapter { tagName -> navigateToTagQuotes(tagName) }

        authorQuotesPagedAdapter.apply {
            viewBinding.recyclerView.adapter = authorQuotesPagedAdapter.withLoadStateFooter(
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
        val authorSlug = safeArgs.authorSlug
        with(authorViewModel) {
            lifecycleScope.launch {
                //load quotes list
                loadInspirationalAuthorQuotes(authorSlug).collectLatest { data ->
                    authorQuotesPagedAdapter.submitData(data)
                }
            }
        }
    }

    private fun navigateToTagQuotes(tagName: String) {
        val action =
            AuthorQuotesFragmentDirections.actionAuthorQuotesFragmentToTagQuotesFragment(
                tagName
            )
        findNavController().navigate(action)
    }
}