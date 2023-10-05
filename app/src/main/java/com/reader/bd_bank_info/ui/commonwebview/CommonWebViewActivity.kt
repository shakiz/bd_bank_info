package com.reader.bd_bank_info.ui.commonwebview

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.databinding.ActivityCommonWebviewBinding
import com.reader.bd_bank_info.utils.TOOLBAR_TITLE
import com.reader.bd_bank_info.utils.WEBVIEW_BUNDLE
import com.reader.bd_bank_info.utils.WEB_URL

class CommonWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommonWebviewBinding

    companion object {
        const val TAG = "CommonWebViewActivity"

        fun createIntent(context: Context?, webUrl: String?, toolbarTitle: String?): Bundle {
            context ?: Intent()
            return Bundle().apply {
                putString(WEB_URL, webUrl)
                putString(TOOLBAR_TITLE, toolbarTitle)
            }
        }
    }

    private var webUrl: String? = null
    private var toolbarTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpIntentData()
        initView()
        initListeners()
    }

    private fun setUpIntentData(){
        val bundle = intent.getBundleExtra(WEBVIEW_BUNDLE)
        webUrl = bundle?.getString(WEB_URL)
        toolbarTitle = bundle?.getString(TOOLBAR_TITLE)
    }

    private fun initView() {
        with(binding) {
            setSupportActionBar(toolbar)
            toolbarTitle?.let {
                toolbar.title = toolbarTitle
            }

            webView.settings.javaScriptEnabled = true
            webView.settings.loadsImagesAutomatically = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.settings.pluginState = WebSettings.PluginState.ON_DEMAND
            webView.settings.mediaPlaybackRequiresUserGesture = false
            webView.settings.mixedContentMode =
                WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            CookieManager.getInstance()
                .setAcceptThirdPartyCookies(webView, true)
            webView.settings.useWideViewPort = true
            webView.settings.loadWithOverviewMode = true
            webView.isFocusableInTouchMode = true
            webView.settings.allowFileAccessFromFileURLs = true
            webView.settings.allowUniversalAccessFromFileURLs = true
            webView.settings.domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                webView.settings.safeBrowsingEnabled = false
            }
            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            progressBar.visibility = View.VISIBLE
            progressBar.progressTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@CommonWebViewActivity,
                        R.color.colorPrimaryDark
                    )
                )
            progressBar.indeterminateDrawable?.setTint(
                ContextCompat.getColor(
                    this@CommonWebViewActivity,
                    R.color.colorPrimary
                )
            )
            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    if (newProgress > 80) {
                        progressBar.visibility = View.GONE
                    }
                }
            }

            webView.webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    progressBar.visibility = View.GONE
                }
            }
            if (webUrl.orEmpty().contains("https://www.youtube.com")) {
                webView.settings.userAgentString =
                    "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17"
            }
            webUrl?.let { webView.loadUrl(it) }
        }
    }

    private fun initListeners(){
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onResume() {
        super.onResume()
        binding.webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.webView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.webView.stopLoading()
        binding.webView.destroy()
    }
}