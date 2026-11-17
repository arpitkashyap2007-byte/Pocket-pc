package com.pocketpc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var desktop: WebView
    private val openFileRequest = 4102

    @Suppress("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        hideSystemUi()

        desktop = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = false
            settings.allowContentAccess = false
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            addJavascriptInterface(PocketBridge(), "PocketPC")
            loadUrl("file:///android_asset/index.html")
        }
        setContentView(desktop)
    }

    private inner class PocketBridge {
        @JavascriptInterface
        fun openAndroidFilePicker() {
            runOnUiThread {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "*/*"
                }
                startActivityForResult(intent, openFileRequest)
            }
        }

        @JavascriptInterface
        fun showMessage(message: String) {
            runOnUiThread {
                Toast.makeText(this@MainActivity, message.take(180), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Android API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == openFileRequest && resultCode == RESULT_OK) {
            val uri: Uri? = data?.data
            val name = uri?.lastPathSegment ?: "File selected"
            val safeName = name.replace("\\", "\\\\").replace("'", "\\'")
            desktop.evaluateJavascript("window.onPocketFileSelected('$safeName')", null)
        }
    }

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            )
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (::desktop.isInitialized && desktop.canGoBack()) desktop.goBack()
        else super.onBackPressed()
    }
}
