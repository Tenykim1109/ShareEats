<h1 align="center">
    <br> 🍲ShareEats🍲
</h1>

![썸네일 테스트](https://user-images.githubusercontent.com/76620764/145583547-cc74c765-eb7d-420a-9dc1-fc3706dc6931.png)

- Wanted Hack.career Hackathon
- 프로젝트 기간: `2021.10.30` ~ `2021.12.10`

<br>
<br>

## Features
- 현위치 또는 선택한 동네를 기준으로 배달 펀딩 게시글 확인 가능
- 선택한 음식점을 기준으로 배달 음식 모집 글 작성
- 작성자와 참여자 간에 채팅 가능
- 참여자가 게시글을 확인하고 메뉴 선택 후 결제를 통해 펀딩 참여
- 최소주문금액을 충족시키면 자동으로 주문 완료
- 주문 완료 직후 펀딩 참여자들에게 주문 완료 푸쉬 알림 전송
- 지난 주문 내역 확인 가능

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
<summary> 📚 AutoCompleteTextView 검색 조건 설정 이슈</summary>
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
AutoCompleteTextView를 사용하면서 키워드에 따라 검색 결과를 다르게 보여주도록 검색 조건을 설정하는데 어려움을 겪음
AutoCompleteTextView의 리스트에 보여줄 커스텀 어댑터를 작성하면서 Filterable 인터페이스를 사용하여 getFilter 함수를 오버라이딩
getFilter 함수 내에 검색 조건을 설정하여 해당 조건에 충족하는 키워드 기준으로 검색 결과 리스트를 반환하는 형식으로 

<br>

</details>

<details markdown="2">
<summary> 📚 카카오페이 리다이렉트 이슈</summary>
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
카카오페이 api 개발 중 웹뷰를 띄워 redirect_moblie_url 로 앱에서 자체적으로 카카오페이 결제를 수행하도록 설계를 했으나,   
제대로 넘어가지 못하고 pg_token을 가져오지 못함   
하여, redirect_pc_url로 수정하여 웹에서 카카오페이를 사용할 때 사용하는 qr 결제 또는 메시지 결제로 대체

<br>

</details>
    
<details markdown="3">
<summary> 📚 네이버 지도 API 커스텀 마커 이슈</summary>    
<br>
<br>
    
```
var storeName = ""

// 매장 이름 가져오는 비동기 코드 내에서 marker 설정
storeRef.child(storeId).child("name").get().addOnSuccessListener {
    val res = GeocodeService().getGeocode(location, getGeocodeCallback())

    Log.d(TAG, "title = $title, storeId = $storeId")
    res.observe(viewLifecycleOwner, { res ->

        executor.execute {
            storeName = it.getValue<String>()!!
            Log.d(TAG, "storeName = $storeName")

            infoWindow = InfoWindow()

            // BackgroundThread에서 마커 정보 초기화
            repeat(1) {
                val post = snapshot.getValue<Post>()
                Log.d(TAG, "post = $post")
                hashMap.put("${post!!.postId}", post)

                for (address in res.addresses) {
                    Log.d(TAG, "store_value = $storeName")
                    Log.d(TAG, "도로명주소 = ${address.roadAddress}")

                    val marker = Marker()
                    marker.position = LatLng(address.y, address.x)
                    marker.icon = MarkerIcons.RED
                    marker.onClickListener = markerListener
                    marker.tag = "제목: $title \n주문 매장: $storeName"
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

                // MainThread에서 지도에 마커 표시
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

                                // intent로 게시글 id를 넘겨줌.
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
    
네이버 지도 API 개발 중 커스텀 마커에 매장 이름이 표시되지 않는 이슈가 있었음.
원인은 background thread에서 데이터를 받아오고 main thread에서 ui 처리를 하도록 설계했으나 firebase는 자체적인 비동기 루프를 가지고 있어 thread와 따로 움직였기 때문.
따라서 firebase 비동기 루프 내에서 background와 main을 나눠서 처리하여 이슈 해결.
    
<br>
    
</details>    

## Screenshot

1️⃣ **로그인**

![와어이프레임_로그인](https://user-images.githubusercontent.com/76620764/145572789-9a08a954-d013-4f92-b4ba-b60773a08f2b.jpg)
<br>

2️⃣ **게시글**

![와이어프레임_게시글](https://user-images.githubusercontent.com/76620764/145572911-2acfd1c3-c3f2-486b-a171-e75d40bf4f63.jpg)
<br>

3️⃣ **채팅**

![와어이프레임_채팅](https://user-images.githubusercontent.com/76620764/145572935-1a1324c2-c700-4c77-bcf8-155949b0317d.jpg)
<br>

4️⃣ **마이페이지**

![와어이프레임_마이페이지](https://user-images.githubusercontent.com/76620764/145572953-9050a6b2-768d-4b51-b494-5d9223e9310a.jpg)
<br>

## Contributor
<table class="tg">
<tbody>
    <tr>
        <td>김민정</td>
        <td>김주환</td>
        <td>나요셉</td>
        <td>백동열</td>
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
        <td>네이버 지도 API, 1:1 채팅, 만든이</td>
        <td>로그인, 회원가입, 마이페이지<br>회원정보 수정, 공지사항</td>
        <td></td>
        <td>게시글 작성, 게시글 목록, 게시글 검색<br>참여하기</td>
    </tr>
</tbody>
</table>
