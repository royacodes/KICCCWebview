package com.irankish.kicccwebview

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.Surface
import com.irankish.kicccwebview.ui.theme.KICCCWebviewTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KICCCWebviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyContent() {

//    val mUrl = "https://mohsen.amjadism.com"
    val mUrl = "https://digitalmenu.sepidz.com"
//    var canGoBack by remember { mutableStateOf(false) }
//    var canGoForward by remember { mutableStateOf(false) }
//
//    val webView = rememberUpdatedState(WebViewConfig(mUrl, canGoBack, canGoForward))
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        TopAppBar(
//            title = { Text("WebView Example") },
////            colors = Color.Blue,
//            actions = {
//                IconButton(onClick = { webView.value.webView.goBack() }) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                }
//                IconButton(onClick = { webView.value.webView.goForward() }) {
//                    Icon(Icons.Default.ArrowForward, contentDescription = "Forward")
//                }
//            }
//        )
//        AndroidView(factory = {
//            WebView(it).apply {
//                settings.javaScriptEnabled = true
//
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//                webViewClient = WebViewClient()
//                loadUrl(mUrl)
//
//            }
//        }, update = {
//            it.loadUrl(mUrl)
//        })
//    }

    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var canGoHome by remember { mutableStateOf(false) }
    var webView: WebView? = null


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("") },
//            backgroundColor  = Color.Blue,
            actions = {
                IconButton(onClick = {
                    webView?.loadUrl(mUrl)

//                    canGoBack = false
//                    canGoForward = false
//                    canGoHome = true
                }) {
                    Icon(Icons.Filled.Home, contentDescription = "Home")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
//                        canGoBack = true
//                        canGoForward = false
//                        canGoHome = false
                        if (canGoBack) {
                            webView?.goBack()
                        }

                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    IconButton(onClick = {
//                        canGoBack = false
//                        canGoForward = true
//                        canGoHome = false
                        if (canGoForward) {
                            webView?.goForward()
                        }
                    }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Forward")
                    }
                }

            },
        )

        AndroidView(
            factory = { context ->
                val webView = WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.domStorageEnabled = true
                    webViewClient = WebViewClient()
                    loadUrl(mUrl)
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                            view?.loadUrl(url)
                            return true
                        }
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            canGoBack = view?.canGoBack() ?: false
                            canGoForward = view?.canGoForward() ?: false
                        }
                    }
                }
                webView
            },
            update = {
                webView = it

//                    webView ->
//                if (canGoHome) {
//                    webView.loadUrl(mUrl)
//                }
//                if (canGoBack) {
//                    webView.goBack()
//                }
//                if (canGoForward) {
//                    webView.goForward()
//                }

            },
            modifier = Modifier.weight(1f)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KICCCWebviewTheme {
        MyContent()
    }
}

data class WebViewConfig(val url: String, val canGoBack: Boolean, val canGoForward: Boolean)
