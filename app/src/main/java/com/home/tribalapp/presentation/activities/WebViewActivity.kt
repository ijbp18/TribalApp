package com.home.tribalapp.presentation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.home.tribalapp.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl(intent.getStringExtra(EXTRA_URL))

        // this will enable the javascript settings
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)
    }

    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }

    companion object {
        private const val EXTRA_URL = "EXTRA_URL"

        /**
         * @param callingContext the calling context
         * @param url the url of the image to show
         *
         * @return the intent needed to come to this activity
         */
        fun getStartingIntent(callingContext: Context, url: String): Intent {
            val intent = Intent(callingContext, WebViewActivity::class.java)
            intent.putExtra(EXTRA_URL, url)
            return intent
        }
    }
}