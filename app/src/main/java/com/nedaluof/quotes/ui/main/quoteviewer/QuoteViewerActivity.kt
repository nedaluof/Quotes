package com.nedaluof.quotes.ui.main.quoteviewer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.nedaluof.domain.model.quote.QuoteModel
import com.nedaluof.quotes.R
import com.nedaluof.quotes.databinding.ActivityQuoteViewerBinding
import com.nedaluof.quotes.ui.base.BaseActivity
import com.nedaluof.quotes.ui.base.BaseViewModel
import com.nedaluof.quotes.util.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteViewerActivity : BaseActivity<ActivityQuoteViewerBinding>() {

  override val bindingVariable = 0
  override val layoutId = R.layout.activity_quote_viewer
  override fun getViewModel(): BaseViewModel? = null

  private lateinit var comingQuoteModel: QuoteModel
  private val preparedQuote : String
    get() =  """
      ❝
        ${comingQuoteModel.content}
                                     ❞
        - ${comingQuoteModel.author}
      """.trimIndent()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    loadComingQuote()
  }

  private fun loadComingQuote() {
    intent?.let {
      it.getParcelableExtra<QuoteModel>(QUOTE_MODEL_KEY)?.let { model ->
        comingQuoteModel = model
        loadQuoteDataToView()
      }
    }
  }

  private fun loadQuoteDataToView() {
    if (::comingQuoteModel.isInitialized) {
      with(viewBinding) {
        quoteContent.text = comingQuoteModel.content
        authorName.text = comingQuoteModel.author
        backBtn.click(::onBackPressed)
        shareBtn.click(::handleShare)
        copyBtn.click(::handleCopy)
      }
    }
  }

  private fun handleShare() {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
      putExtra(
        Intent.EXTRA_TEXT, preparedQuote
      )
    }
    startActivity(
      Intent.createChooser(
        shareIntent, getString(R.string.share_message_label)
      )
    )
  }

  private fun handleCopy() {
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    clipboard?.setPrimaryClip(
      ClipData.newPlainText(
        getString(R.string.copy_message_label), preparedQuote
      )
    )
    toast(R.string.copy_success_label)
  }

  companion object {
    private const val QUOTE_MODEL_KEY = "QUOTE_MODEL_KEY"
    fun getIntent(context: Context, quoteModel: QuoteModel) =
      Intent(context, QuoteViewerActivity::class.java).also {
        it.putExtra(QUOTE_MODEL_KEY, quoteModel)
      }
  }
}