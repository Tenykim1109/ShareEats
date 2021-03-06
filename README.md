<h1 align="center">
    <br> ๐ฒShareEats๐ฒ
</h1>

![์ธ๋ค์ผ ํ์คํธ](https://user-images.githubusercontent.com/76620764/145583547-cc74c765-eb7d-420a-9dc1-fc3706dc6931.png)

- Wanted Hack.career Hackathon
- ํ๋ก์ ํธ ๊ธฐ๊ฐ: `2021.10.30` ~ `2021.12.10`

<br>
<br>

## Features
- ํ์์น ๋๋ ์ ํํ ๋๋ค๋ฅผ ๊ธฐ์ค์ผ๋ก ๋ฐฐ๋ฌ ํ๋ฉ ๊ฒ์๊ธ ํ์ธ ๊ฐ๋ฅ
- ์ ํํ ์์์ ์ ๊ธฐ์ค์ผ๋ก ๋ฐฐ๋ฌ ์์ ๋ชจ์ง ๊ธ ์์ฑ
- ์์ฑ์์ ์ฐธ์ฌ์ ๊ฐ์ ์ฑํ ๊ฐ๋ฅ
- ์ฐธ์ฌ์๊ฐ ๊ฒ์๊ธ์ ํ์ธํ๊ณ  ๋ฉ๋ด ์ ํ ํ ๊ฒฐ์ ๋ฅผ ํตํด ํ๋ฉ ์ฐธ์ฌ
- ์ต์์ฃผ๋ฌธ๊ธ์ก์ ์ถฉ์กฑ์ํค๋ฉด ์๋์ผ๋ก ์ฃผ๋ฌธ ์๋ฃ
- ์ฃผ๋ฌธ ์๋ฃ ์งํ ํ๋ฉ ์ฐธ์ฌ์๋ค์๊ฒ ์ฃผ๋ฌธ ์๋ฃ ํธ์ฌ ์๋ฆผ ์ ์ก
- ์ง๋ ์ฃผ๋ฌธ ๋ด์ญ ํ์ธ ๊ฐ๋ฅ

## Tech
- Third Party
  - Firebase Realtime Database
  - Firebase Storage
  - Firebase Cloud Message
  - NaverMap SDK
  - Naver Cloud API (Geocoding, Reverse Geocoding)
  - KaKao Pay API

- UI
  - ConstraintLayout
  - BottomNavigation
  - TabLayout
  - Fragment
  - AutoCompleteTextView
  - Toolbar
  - Custom ListView

- Language
  - Kotlin
  - Java
 
- IDE
  - Android Studio
  - Spring Tool Suite


## Problem

<details markdown="1">
<summary> ๐ AutoCompleteTextView ๊ฒ์ ์กฐ๊ฑด ์ค์  ์ด์</summary>
<br>
<br>

```
override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val queryString = p0?.toString()

                Log.d(TAG, "performFiltering: ${queryString}")

                var filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    postList
                else
                    postList.filter {
                        it.title.contains(queryString) || it.content.contains(queryString) || it.place.contains(queryString)
                    }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mList = p1!!.values as MutableList<Post>
                notifyDataSetChanged()
            }

        }
    }
```
AutoCompleteTextView๋ฅผ ์ฌ์ฉํ๋ฉด์ ํค์๋์ ๋ฐ๋ผ ๊ฒ์ ๊ฒฐ๊ณผ๋ฅผ ๋ค๋ฅด๊ฒ ๋ณด์ฌ์ฃผ๋๋ก ๊ฒ์ ์กฐ๊ฑด์ ์ค์ ํ๋๋ฐ ์ด๋ ค์์ ๊ฒช์
AutoCompleteTextView์ ๋ฆฌ์คํธ์ ๋ณด์ฌ์ค ์ปค์คํ ์ด๋ํฐ๋ฅผ ์์ฑํ๋ฉด์ Filterable ์ธํฐํ์ด์ค๋ฅผ ์ฌ์ฉํ์ฌ getFilter ํจ์๋ฅผ ์ค๋ฒ๋ผ์ด๋ฉ
getFilter ํจ์ ๋ด์ ๊ฒ์ ์กฐ๊ฑด์ ์ค์ ํ์ฌ ํด๋น ์กฐ๊ฑด์ ์ถฉ์กฑํ๋ ํค์๋ ๊ธฐ์ค์ผ๋ก ๊ฒ์ ๊ฒฐ๊ณผ ๋ฆฌ์คํธ๋ฅผ ๋ฐํํ๋ ํ์์ผ๋ก 

<br>

</details>

<details markdown="2">
<summary> ๐ ์นด์นด์คํ์ด ๋ฆฌ๋ค์ด๋ ํธ ์ด์</summary>
<br>
<br>

```
val readyResponse = Response.Listener<String> { response ->
    Log.d(TAG, "readyResponse: $response")

    var parser = JsonParser()
    var element = parser.parse(response)

    var url = element.asJsonObject.get("next_redirect_pc_url").asString
    var tid = element.asJsonObject.get("tid").asString

    Log.d(TAG, "url: $url")
    Log.d(TAG, "tid: $tid")
    
    webView.loadUrl(url)
    tidPin = tid
}
```
์นด์นด์คํ์ด api ๊ฐ๋ฐ ์ค ์น๋ทฐ๋ฅผ ๋์ redirect_moblie_url ๋ก ์ฑ์์ ์์ฒด์ ์ผ๋ก ์นด์นด์คํ์ด ๊ฒฐ์ ๋ฅผ ์ํํ๋๋ก ์ค๊ณ๋ฅผ ํ์ผ๋,   
์ ๋๋ก ๋์ด๊ฐ์ง ๋ชปํ๊ณ  pg_token์ ๊ฐ์ ธ์ค์ง ๋ชปํจ   
ํ์ฌ, redirect_pc_url๋ก ์์ ํ์ฌ ์น์์ ์นด์นด์คํ์ด๋ฅผ ์ฌ์ฉํ  ๋ ์ฌ์ฉํ๋ qr ๊ฒฐ์  ๋๋ ๋ฉ์์ง ๊ฒฐ์ ๋ก ๋์ฒด

<br>

</details>
    
<details markdown="3">
<summary> ๐ ๋ค์ด๋ฒ ์ง๋ API ์ปค์คํ ๋ง์ปค ์ด์</summary>    
<br>
<br>
    
```
var storeName = ""

// ๋งค์ฅ ์ด๋ฆ ๊ฐ์ ธ์ค๋ ๋น๋๊ธฐ ์ฝ๋ ๋ด์์ marker ์ค์ 
storeRef.child(storeId).child("name").get().addOnSuccessListener {
    val res = GeocodeService().getGeocode(location, getGeocodeCallback())

    Log.d(TAG, "title = $title, storeId = $storeId")
    res.observe(viewLifecycleOwner, { res ->

        executor.execute {
            storeName = it.getValue<String>()!!
            Log.d(TAG, "storeName = $storeName")

            infoWindow = InfoWindow()

            // BackgroundThread์์ ๋ง์ปค ์ ๋ณด ์ด๊ธฐํ
            repeat(1) {
                val post = snapshot.getValue<Post>()
                Log.d(TAG, "post = $post")
                hashMap.put("${post!!.postId}", post)

                for (address in res.addresses) {
                    Log.d(TAG, "store_value = $storeName")
                    Log.d(TAG, "๋๋ก๋ช์ฃผ์ = ${address.roadAddress}")

                    val marker = Marker()
                    marker.position = LatLng(address.y, address.x)
                    marker.icon = MarkerIcons.RED
                    marker.onClickListener = markerListener
                    marker.tag = "์ ๋ชฉ: $title \n์ฃผ๋ฌธ ๋งค์ฅ: $storeName"
                    marker.subCaptionText = "$postId"

                    placeMarkers += marker
                    placeInfoList += MarkerInfo(marker, title, storeName)
                }
            }

            handler.post {
                infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
                    override fun getText(infoWindow: InfoWindow): CharSequence {
                        return infoWindow.marker?.tag as CharSequence ?: ""
                    }
                }

                // MainThread์์ ์ง๋์ ๋ง์ปค ํ์
                placeInfoList.forEach { markerInfo ->
                    run {
                        markerInfo.marker.map = naverMap
                        Log.d(TAG, "title = ${markerInfo.title}, store = ${markerInfo.storeName}")
                        infoWindow.open(markerInfo.marker)
                        infoWindow.onClickListener = object : Overlay.OnClickListener {
                            override fun onClick(p0: Overlay): Boolean {
                                val infoWindow = p0 as InfoWindow

                                Log.d(TAG, "${infoWindow.marker!!.subCaptionText} clicked.")
                                Log.d(TAG, "${hashMap.get(infoWindow.marker!!.subCaptionText)}")

                                // intent๋ก ๊ฒ์๊ธ id๋ฅผ ๋๊ฒจ์ค.
                                val intent = Intent(requireContext(), PostInfoActivity::class.java)
                                intent.putExtra("postId", infoWindow.marker!!.subCaptionText.toInt())
                                startActivity(intent)
                                return false
                            }
                        }
                    }
                }

                Log.d(TAG, "place size = ${placeMarkers.size}")
                Log.d(TAG, "info size = ${placeInfoList.size}")
            }
        }
    })
}
    
```    
    
๋ค์ด๋ฒ ์ง๋ API ๊ฐ๋ฐ ์ค ์ปค์คํ ๋ง์ปค์ ๋งค์ฅ ์ด๋ฆ์ด ํ์๋์ง ์๋ ์ด์๊ฐ ์์์.
์์ธ์ background thread์์ ๋ฐ์ดํฐ๋ฅผ ๋ฐ์์ค๊ณ  main thread์์ ui ์ฒ๋ฆฌ๋ฅผ ํ๋๋ก ์ค๊ณํ์ผ๋ firebase๋ ์์ฒด์ ์ธ ๋น๋๊ธฐ ๋ฃจํ๋ฅผ ๊ฐ์ง๊ณ  ์์ด thread์ ๋ฐ๋ก ์์ง์๊ธฐ ๋๋ฌธ.
๋ฐ๋ผ์ firebase ๋น๋๊ธฐ ๋ฃจํ ๋ด์์ background์ main์ ๋๋ ์ ์ฒ๋ฆฌํ์ฌ ์ด์ ํด๊ฒฐ.
    
<br>
    
</details>
    
    
<details markdown="4">
<summary> ๐ ์ฝ๋ ํต์ผ ์ด์ </summary>
<br>
<br>

```
๐ฆ com.sharedwanted.shareeats
 โฃ ๐ config
 โ โ ๐ ApplicationClass
 โฃ ๐ database
 โฃ ๐ service
 โฃ ๐ src
 โ โ ๐ api
 โ โ ๐ main
 โ โ ๐ splash
 โฃ ๐ util
 โ โ ๐ SharedPreferencesUtil
```

๊ฐ์ ์๊ฐํ๋ ๊ตฌ์กฐ๋ฅผ ํต์ผํ๊ธฐ ์ํด ์ ์ฒด์ ์ผ๋ก ํํ๋ฆฟํ ์์ผ์ ๊ฐ๋ฐ์ ์์ํจ

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- margin ๊ฐ -->
    <dimen name="activity_margin_32dp">32dp</dimen>
    <dimen name="activity_margin_16dp">16dp</dimen>
    <dimen name="activity_margin_8dp">8dp</dimen>
    <dimen name="appbar_margin_54dp">54dp</dimen>

    <!-- ํฐํธ ํฌ๊ธฐ ๊ฐ -->
    <dimen name="appbar_text_size_18sp">18sp</dimen>
    <dimen name="header_text_size_16sp">16sp</dimen>
    <dimen name="body_text_size_14sp">14sp</dimen>
    <dimen name="explain_text_size_10sp">10sp</dimen>
</resources>
```

์ฌ์ด์ฆ ๊ธฐ์ค ์์ด ์  ๊ฐ๊ฐ์ผ๋ก UI๋ฅผ ์ ์ํ๋ค ๋ณด๋ ์ ์ฒด์ ์ธ ํต์ผ์ฑ์ด ๋จ์ด์ง <br>
์ด๋ฅผ ํด๊ฒฐํ๊ธฐ ์ํด dimens, themes ๋ฑ ๊ฐ์ ์ ๊ทน ํ์ฉํ์์ผ๋ฉฐ <br>
๋๋ถ์ด ์์ ์ฌํญ์ด ์๊ฒผ์ ์ ์ ์ง ๋ณด์๊ฐ ๊ฐํธํด์ง๋ค๋ ๊ฒ์ ๊นจ๋ฌ์


<br>

</details>

## Screenshot

1๏ธโฃ **๋ก๊ทธ์ธ**

![์์ด์ดํ๋ ์_๋ก๊ทธ์ธ](https://user-images.githubusercontent.com/76620764/145572789-9a08a954-d013-4f92-b4ba-b60773a08f2b.jpg)
<br>

2๏ธโฃ **๊ฒ์๊ธ**

![์์ด์ดํ๋ ์_๊ฒ์๊ธ](https://user-images.githubusercontent.com/76620764/145572911-2acfd1c3-c3f2-486b-a171-e75d40bf4f63.jpg)
<br>

3๏ธโฃ **์ฑํ**

![์์ด์ดํ๋ ์_์ฑํ](https://user-images.githubusercontent.com/76620764/145572935-1a1324c2-c700-4c77-bcf8-155949b0317d.jpg)
<br>

4๏ธโฃ **๋ง์ดํ์ด์ง**

![์์ด์ดํ๋ ์_๋ง์ดํ์ด์ง](https://user-images.githubusercontent.com/76620764/145572953-9050a6b2-768d-4b51-b494-5d9223e9310a.jpg)
<br>

## Contributor
<table class="tg">
<tbody>
    <tr>
        <td>๊น๋ฏผ์ </td>
        <td>๊น์ฃผํ</td>
        <td>๋์์</td>
        <td>๋ฐฑ๋์ด</td>
    </tr>
    <tr>
        <td><a href="https://github.com/Tenykim1109">@Tenykim1109</a></td>
        <td><a href="https://github.com/juhwankim-dev">@juhwankim-dev</a></td>
        <td><a href="https://github.com/JosephNaa">@JosephNaa</a></td>
        <td><a href="https://github.com/micro155">@micro155</a></td>
    </tr>
    <tr>
        <td><img src="https://avatars.githubusercontent.com/u/48265915?v=4" width="300px"/></td>
        <td><img src="https://user-images.githubusercontent.com/76620764/145577637-1cb20f92-d076-4e3f-91d4-9719a1621542.jpg"  width="300px"/></td>
        <td><img src="https://avatars.githubusercontent.com/u/17241871?v=4"  width="300px"/></td>
        <td><img src="https://avatars.githubusercontent.com/u/69238456?s=400&u=849688e4a8675e363dc45a29b8d3e1cb6d468a01&v=4"  width="300px"/></td>
    </tr>
    <tr>
        <td>๋ค์ด๋ฒ ์ง๋ API, 1:1 ์ฑํ, ๋ง๋ ์ด</td>
        <td>๋ก๊ทธ์ธ, ํ์๊ฐ์, ๋ง์ดํ์ด์ง<br>ํ์์ ๋ณด ์์ , ๊ณต์ง์ฌํญ</td>
        <td>์นด๋์ ๋ณด ์ ์ฅ, ๊ฒฐ์ ํ๊ธฐ<br>์นด์นด์คํ์ด API</td>
        <td>๊ฒ์๊ธ ์์ฑ, ๊ฒ์๊ธ ๋ชฉ๋ก, ๊ฒ์๊ธ ๊ฒ์, ์ฐธ์ฌํ๊ธฐ</td>
    </tr>
</tbody>
</table>
