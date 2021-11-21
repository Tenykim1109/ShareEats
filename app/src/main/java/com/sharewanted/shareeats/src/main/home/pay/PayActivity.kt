package com.sharewanted.shareeats.src.main.home.pay

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.sharewanted.shareeats.databinding.ActivityPayBinding
import java.lang.Exception
import java.security.MessageDigest

private const val TAG = "PayActivity_싸피"
class PayActivity : AppCompatActivity() {

    lateinit var requestQueue: RequestQueue
    lateinit var productName: String
    lateinit var productPrice: String

    lateinit var webView: WebView
    lateinit var gson: Gson
    lateinit var myWebViewClient: MyWebViewClient

    lateinit var tidPin: String
    lateinit var pgToken: String

    inner class MyWebViewClient: WebViewClient() {
        val errorListener = Response.ErrorListener { error ->
            Log.d(TAG, "Error: $error")
        }

        val readyResponse = Response.Listener<String> { response ->
            Log.d(TAG, "onResponse: $response")

            var parser = JsonParser()
            var element = parser.parse(response)

            var url = element.asJsonObject.get("next_redirect_mobile_url").asString
            var tid = element.asJsonObject.get("tid").asString

            Log.d(TAG, "url: $url")
            Log.d(TAG, "tid: $tid")

            webView.loadUrl(url)
            tidPin = tid
        }

        val readyRequest = object: StringRequest(
            Method.POST,
            "https://kapi.kakao.com/v1/payment/ready",
            readyResponse,
            errorListener
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {

                Log.d(TAG, "getParams: name - $productName")
                Log.d(TAG, "getParams: price - $productPrice")

                var params = HashMap<String, String>()
                params["cid"] = "TC0ONETIME"
                params["partner_order_id"] = "partner_order_id"; // 가맹점 주문 번호
                params["partner_user_id"] = "partner_user_id"; // 가맹점 회원 아이디
                params["item_name"] = productName; // 상품 이름
                params["quantity"] = "1"; // 상품 수량
                params["total_amount"] = productPrice; // 상품 총액
                params["tax_free_amount"] = "0"; // 상품 비과세
                params["approval_url"] = "https://developers.kakao.com/success"; // 결제 성공시 돌려 받을 url 주소
                params["cancel_url"] = "https://developers.kakao.com/cancel"; // 결제 취소시 돌려 받을 url 주소
                params["fail_url"] = "https://developers.kakao.com/fail"; // 결제 실패시 돌려 받을 url 주소

                Log.d(TAG, "getParams: $params")
                return params;
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                var headers = HashMap<String, String>()
                headers["Authorization"] = "KakaoAK " + "a1f2998651e0caedb45555dbf40c764c"
//                headers["Content-type"] = "application/x-www-form-urlencoded;charset=utf-8"
                Log.d(TAG, "getHeaders: $headers")
                return headers
            }
        }

        var approvalResponse = Response.Listener<String> { response ->
                Log.d(TAG, "onResponse: $response")
        }

        var approvalRequest = object : StringRequest(
            Method.POST,
            "https://kapi.kakao.com/v1/payment/approve",
            approvalResponse,
            errorListener
        ) {
            override fun getParams(): MutableMap<String, String> {
                var params = HashMap<String, String>()
                params.put("cid", "TC0ONETIME");
                params.put("tid", tidPin);
                params.put("partner_order_id", "1001");
                params.put("partner_user_id", "gorany");
                params.put("pg_token", pgToken);
                params.put("total_amount", productPrice);
                return params;
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Authorization"] = "KakaoAK " + "a1f2998651e0caedb45555dbf40c764c"
                return headers
            }
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            url: String
        ): Boolean {
            Log.d(TAG, "shouldOverrideUrlLoading: $url")

            if (url != null && url.contains("pg_token=")) {
                var pg_token = url.substring(url.indexOf("pg_token=") + 9)
                pgToken = pg_token

                requestQueue.add(approvalRequest)
            } else if (url != null && url.startsWith("intent://")) {
                try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    val existPackage = intent.`package`?.let {
                        packageManager.getLaunchIntentForPackage(
                            it
                        )
                    }
                    if (existPackage != null) {
                        startActivity(intent)
                    }
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            view?.loadUrl(url)
            return false
        }
    }

    lateinit var binding: ActivityPayBinding

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
//        try {
//            val packageInfo = packageManager.getPackageInfo(
//                packageName,
//                PackageManager.GET_SIGNING_CERTIFICATES
//            )
//            val signingInfo = packageInfo.signingInfo.apkContentsSigners
//
//            for (signature in signingInfo) {
//                val messageDigest = MessageDigest.getInstance("SHA")
//                messageDigest.update(signature.toByteArray())
//                val keyHash = String(Base64.encode(messageDigest.digest(), 0))
//                Log.d(TAG, "onCreate: keyHash $keyHash")
//            }
//        } catch (e: Exception) {
//            Log.d(TAG, "onCreate: exception: ${e.toString()}")
//        }

        productName = intent.getStringExtra("name").toString()
        productPrice = intent.getStringExtra("price").toString()

        requestQueue = Volley.newRequestQueue(this)
        myWebViewClient = MyWebViewClient()
        webView = binding.activityPayWebView
        gson = Gson()

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = myWebViewClient

        requestQueue.add(myWebViewClient.readyRequest)

//        Log.d(TAG, "onCreate: $requestQueue")
    }
}