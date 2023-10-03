package com.reader.bd_bank_info.ui.commonwebview

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.provider.Browser
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.databinding.ActivityCommonWebviewBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class CommonWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommonWebviewBinding

    companion object {
        const val TAG = "CommonWebViewActivity"
        const val WEB_URL = "web-url"

        fun createIntent(context: Context?, webUrl: String): Intent {
            context ?: Intent()
            return Intent(context, CommonWebViewActivity::class.java).apply {
                putExtra(WEB_URL, webUrl)
            }
        }
    }

    private val BROWSER_URI_SCHEMA: Pattern = Pattern.compile(
        "(?i)" // switch on case insensitive matching
                + "(" // begin group for schema
                + "(?:http|https|file):\\/\\/"
                + "|(?:inline|data|about|chrome|javascript):"
                + ")"
                + "(.*)"
    )


    private var webUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonWebviewBinding.inflate(layoutInflater)

        webUrl = intent?.getStringExtra(WEB_URL).orEmpty()

        initView()
    }


    private fun initView() {
        with(binding) {
            setSupportActionBar(binding.toolbar)

            webView?.settings?.loadsImagesAutomatically = true
            webView?.settings?.javaScriptEnabled = true
            webView?.settings?.javaScriptCanOpenWindowsAutomatically = true
            webView?.settings?.pluginState = WebSettings.PluginState.ON_DEMAND
            webView?.settings?.mediaPlaybackRequiresUserGesture = false
            if (Build.VERSION.SDK_INT >= 21) {
                webView.settings.mixedContentMode =
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                CookieManager.getInstance()
                    .setAcceptThirdPartyCookies(webView, true)
            }
            webView?.settings?.useWideViewPort = true
            webView?.settings?.loadWithOverviewMode = true
            webView?.isFocusableInTouchMode = true
            webView?.settings?.allowFileAccessFromFileURLs = true
            webView?.settings?.allowUniversalAccessFromFileURLs = true
            webView?.settings?.domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                webView?.settings?.safeBrowsingEnabled = false
            }
            webView?.settings?.cacheMode = WebSettings.LOAD_NO_CACHE
            try {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    // only for gingerbread and newer versions
                    progressBar?.visibility = View.VISIBLE
                    progressBar?.progressTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                this@CommonWebViewActivity,
                                R.color.colorPrimaryDark
                            )
                        )
                    progressBar?.indeterminateDrawable?.setTint(
                        ContextCompat.getColor(
                            this@CommonWebViewActivity,
                            R.color.colorPrimary
                        )
                    )
                }
            } catch (ex: Exception) {

            }
            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    if (newProgress > 70) {
                        progressBar?.visibility = View.GONE
                    }
                }
            }


            webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    URL: String?
                ): Boolean {
                    URL?.let { url ->
                        if (URLUtil.isNetworkUrl(url)) {
                            if ((url.contains("https://www.youtube.com/watch")
                                        && url.contains("feature=emb_rel_end"))
                                || url.contains("https://m.youtube.com")
                            )
                                return true
                            return false
                        }
                        val isLaunch =
                            startBrowsingIntent(this@CommonWebViewActivity, url)
                        if (isLaunch) {
                            if (url.contains(
                                    "intent://toffeelive.page.link",
                                    false
                                )
                            ) {
                                finish()
                            }
                        }

                    }
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar?.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    progressBar?.visibility = View.GONE
                }


            }
            if (webUrl.contains("https://www.youtube.com")) {
                webView.settings.userAgentString =
                    "Mozilla/5.0 (Linux; U; Android 5.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17"

            }
            webUrl.let { webView.loadUrl(it) }
        }
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

    private fun startBrowsingIntent(context: Context, url: String): Boolean {
        // Perform generic parsing of the URI to turn it into an Intent.
        val intent: Intent = try {
            Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        } catch (ex: Exception) {
            Log.w(TAG, "Bad URI $url $ex")
            return false
        }
        // Check for regular URIs that WebView supports by itself, but also
        // check if there is a specialized app that had registered itself
        // for this kind of an intent.
        val m: Matcher = BROWSER_URI_SCHEMA.matcher(url)
        if (m.matches() && !isSpecializedHandlerAvailable(context, intent)) {
            return false
        }
        // Sanitize the Intent, ensuring web pages can not bypass browser
        // security (only access to BROWSABLE activities).
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.component = null
        val selector = intent.selector
        if (selector != null) {
            selector.addCategory(Intent.CATEGORY_BROWSABLE)
            selector.component = null
        }

        // Pass the package name as application ID so that the intent from the
        // same application can be opened in the same tab.
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName())
        try {
            Log.e("errorurl", "$url")
            context.startActivity(intent)
            return true
        } catch (ex: ActivityNotFoundException) {
            Log.w(TAG, "No application can handle $url")
        } catch (ex: SecurityException) {
            // This can happen if the Activity is exported="true", guarded by a permission, and sets
            // up an intent filter matching this intent. This is a valid configuration for an
            // Activity, so instead of crashing, we catch the exception and do nothing. See
            // https://crbug.com/808494 and https://crbug.com/889300.
            Log.w(TAG, "SecurityException when starting intent for $url")
        }
        return false
    }

    /**
     * Search for intent handlers that are specific to the scheme of the URL in the intent.
     */
    private fun isSpecializedHandlerAvailable(
        context: Context,
        intent: Intent
    ): Boolean {
        val pm = context.packageManager
        val handlers = pm.queryIntentActivities(
            intent,
            PackageManager.GET_RESOLVED_FILTER
        )
        if (handlers == null || handlers.size == 0) {
            return false
        }
        for (resolveInfo in handlers) {
            if (!isNullOrGenericHandler(resolveInfo.filter)) {
                return true
            }
        }
        return false
    }

    private fun isNullOrGenericHandler(filter: IntentFilter?): Boolean {
        return (filter == null
                || filter.countDataAuthorities() == 0 && filter.countDataPaths() == 0)
    }
}