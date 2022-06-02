package com.swc.common.ui.fragment

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.viewbinding.ViewBinding
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewFeature
import com.swc.common.R
import com.swc.common.databinding.DrawerToolbarBinding
import com.swc.common.databinding.FragmentWebviewBaseBinding
import com.swc.common.util.LOG
import com.swc.common.util.PopupDialogUtil
//import kotlinx.android.synthetic.main.fragment_webview_base.*


/**
Created by sangwn.choi on2020-07-01

 **/
abstract class BaseWebviewFragment : BaseFragment<FragmentWebviewBaseBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWebviewBaseBinding = {
            inflater, container, attachToParent -> FragmentWebviewBaseBinding.inflate(inflater, container, attachToParent)
    }

    open var initialUrl: String? = null

//    override val layoutId: Int
//        get() = R.layout.fragment_webview_base

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            if (containsKey(BUNDLE_KEY_WEB_URL)) {
                initialUrl = getString(BUNDLE_KEY_WEB_URL)
            }
        }
        LOG.e("initial url: $initialUrl")
    }

    override fun setLayout() {
        //init webview
        loadingView = binding.lvWeb

        binding.wvBase?.run {
            webViewClient =
                CustomWebViewClient(this@BaseWebviewFragment)
            webChromeClient =
                CustomChromeClient(this@BaseWebviewFragment)
            val set: WebSettings = settings
            set.loadWithOverviewMode = true
            set.useWideViewPort = true
            set.javaScriptEnabled = true
//            set.builtInZoomControls = true
//            set.displayZoomControls = false

            initialUrl?.let {
                showLoading()
                loadUrl(it)
            }
        }

    }

    class CustomChromeClient(private val baseWebviewFragment: BaseWebviewFragment? = null) : WebChromeClient() {

        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
//
//            baseWebviewFragment?.run {
//                PopupDialogUtil.showJsAlert(context, parentFragmentManager, message)
//            }


            //Let the client handle the alert dialog.
//            return true

            /**
             * 팝업에서 주고받는 데이터가 있을 수도 있기 때문에 앱에서 팝업은 별도로 처리하지 않는다.
             */
            return super.onJsAlert(view, url, message, result)
        }
    }

    class CustomWebViewClient(private val baseWebviewFragment: BaseWebviewFragment? = null) : WebViewClientCompat() {
        //TODO: onPageFinished,onPageStarted,shouldOverrideUrlLoading override

        override fun onPageFinished(view: WebView?, url: String?) {
            LOG.e("swc", "onPageFinished $url")
            baseWebviewFragment?.hideLoading()
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            LOG.e("swc", "onPageStarted $url")
//            baseWebviewFragment?.showLoading()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            LOG.e(
                "swc",
                "shouldOverrideUrlLoading url $url"
            )
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            baseWebviewFragment?.run {
                context?.run {
                    PopupDialogUtil.showWebviewErrorPopup(context, parentFragmentManager, description) {
                        parentFragmentManager.popBackStack()
                    }
                }

            }
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceErrorCompat) {
            baseWebviewFragment?.run {
                context?.run {
                    hideLoading()

                    if (Build.VERSION.SDK_INT < 21) {
                        PopupDialogUtil.showWebviewErrorPopup(context, parentFragmentManager) {
                            parentFragmentManager.popBackStack()
                        }
                        return
                    }
                    if (!WebViewFeature.isFeatureSupported(WebViewFeature.WEB_RESOURCE_ERROR_GET_CODE)
                        || !WebViewFeature.isFeatureSupported(
                            WebViewFeature.WEB_RESOURCE_ERROR_GET_DESCRIPTION
                        )
                    ) {
                        // If the WebView APK drops supports for these APIs in the future, simply do nothing.
                        PopupDialogUtil.showWebviewErrorPopup(context, parentFragmentManager) {
                            parentFragmentManager.popBackStack()
                        }
                        return
                    }
                    if (request.isForMainFrame) {
                        onReceivedError(
                            view,
                            error.errorCode, error.description.toString(),
                            request.url.toString()
                        )
                    }
                }

            }

        }

//        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//            LOG.e(
//                "swc",
//                "shouldOverrideUrlLoading req $request"
//            )
//            return super.shouldOverrideUrlLoading(view, request)
//        }
//
//        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
//            LOG.e("onReceivedError $error, ${error?.description}")
//            //show popup
//            super.onReceivedError(view, request, error)
//        }
    }

    override fun onClick(p0: View?) {
    }

    companion object {
        const val BUNDLE_KEY_WEB_URL = "bkdslwebkfzl"
    }
}