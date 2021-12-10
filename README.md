# ShareEats
(대충 여기 어디쯤 썸네일)
- Wanted Hack.career Hackathon
- 프로젝트 기간: `2021.10.30` ~ `2021.12.10`

## Features
- 특징1
- 특징2
- 특징3
- 특징4

## Tech
- MVVM
- LiveData

.
.
.


## Problem

📚 LoginFragment.kt
```
private fun initLottie() {
    binding.splash.addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            if (FourMostPreference.getFirstVisit()) {
                startActivity(Intent(this@SplashActivity, ServiceExplainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
            }
            finish()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }
    })
}
```
이 코드에서 이런 문제가 있어 이렇게 해결을 블라블라

## Screenshot

1️⃣ 로그인

![와어이프레임_로그인](https://user-images.githubusercontent.com/76620764/145572789-9a08a954-d013-4f92-b4ba-b60773a08f2b.jpg)
<br>

2️⃣ 게시글
![와이어프레임_게시글](https://user-images.githubusercontent.com/76620764/145572911-2acfd1c3-c3f2-486b-a171-e75d40bf4f63.jpg)
<br>

3️⃣ 채팅
![와어이프레임_채팅](https://user-images.githubusercontent.com/76620764/145572935-1a1324c2-c700-4c77-bcf8-155949b0317d.jpg)
<br>

4️⃣ 마이페이지
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
        <td><a href="">@4z7l</a></td>
        <td><a href="https://github.com/juhwankim-dev">@juhwankim-dev</a></td>
        <td><a href="">@sgh002400</a></td>
        <td><a href="">@sgh002400</a></td>
    </tr>
    <tr>
        <td><img src="" width="300px"/></td>
        <td><img src="/wiki/contributor/sgh002400.png"  width="300px"/></td>
        <td><img src=""  width="300px"/></td>
        <td><img src=""  width="300px"/></td>
    </tr>
    <tr>
        <td></td>
        <td>저는 이런걸 구현했습니다~ 블라블라~</td>
        <td></td>
        <td></td>
    </tr>
</tbody>
</table>
