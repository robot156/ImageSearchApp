# 이미지 검색 앱

이미지검색앱은 MVVM 아키텍처를 기반으로 하고 있고 Hilt, Coroutines, Flow, Jetpack을 사용하여 모던 안드로이드 앱개발을 지향하고 있습니다.
<br/>
<br/>

## Screenshots
<img src="https://user-images.githubusercontent.com/66992191/226358224-c456b77e-14c4-4a41-ac45-c90bf8e09626.png" width="1800"/>
<br/>
<br/>

## 기술 스택 및 오픈소스 라이브러리

* <b>최소 API 레벨 21</b>
* <b>Kotlin 기반, 코루틴 + Flow 를 사용한 비동기 처리</b>
* <b>Jetpack</b>
	* Lifecycle
	* ViewModel
	* DataBinding
	* Room
	* Hilt
	* Paging3
* <b>Architecture</b>
 	* Clean Architecture
	* MVVM Architecture
	* Repository Pattern
* <b>Retrofit2 & OkHttp3</b>
	* Rest Api 통신과 Http 로그 추적
* <b>Kotlin Serializable</b>
	* JSON 파싱 라이브러리
* <b>Timber</b>

## Architecture OverView
![Architecture](https://user-images.githubusercontent.com/66992191/227775864-93339a3a-5e36-4655-9ae7-b25ad119a0ce.png)

각 계층은 단방향 이벤트/데이터 흐름을 따릅니다. 
UI Layer는 사용자 이벤트를 Domain Layer로 내보내고 Domain Layer는 데이터를 Data Layer로 전송합니다. Data Layer는 스트림으로 데이터를 레포지토리에 노출합니다.

<br/>

## UI Layer
<img width="500" alt="Ui Layer" src="https://user-images.githubusercontent.com/66992191/227774361-38160f90-10f7-4d53-a2f8-9cbd367f482e.png">

View에서는 사용자 Event를 ViewModel로 전달하고 ViewModel을 관찰하여 변경된 데이터를 UI에 노출하는 역할을 합니다.</p>
ViewModel은 View에서 전달 받은 Event Data를 하위 계층인 Domain UseCase에 전달하고 데이터의 상태를 Flow Stream을 통해 관찰하여 Flow ViewHolder인 StateFlow를 통해 View에게 변경사항을 알려줍니다.

<br/>

## Domain Layer
<img width="500" alt="Domain Layer" src="https://user-images.githubusercontent.com/66992191/227774941-8cbf097c-8ca1-4119-980f-448d4dd9e35f.png">

비즈니스 로직을 담당하는 계층입니다. 변경될 수 있는 모든 외부 세계로부터 독립되어 있는 특징을 갖고 있습니다. </p>
데이터 레이어와의 의존성을 느슨하게 만들기 위해 Repository pattern을 통해 Interface를 주입받아서 데이터 레이어에 접근합니다.

## Data Layer

<img width="500" alt="Data Layer" src="https://user-images.githubusercontent.com/66992191/227775569-46cbdde7-1a66-4727-b9b3-34ca44dbff6f.png">

클린아키텍처에서, 가장 바깥층에 있는 계층으로 Retrofit을 통해 Remote 데이터를 주고받고, Room을 통해 로컬 데이터베이스에 접근하여 데이터를 수정, 조회합니다. <p/>
필요에 따라 이 계층에서 Remote 데이터를 로컬에 저장하여 데이터를 관리하는 플로우를 작성할 수도 있습니다. 


## Open API
<img src="https://user-images.githubusercontent.com/66992191/226358912-cd55f085-5763-415f-a167-ec84659bfe28.png" width="350"/>
https://unsplash.com/ko
UnSplash API는 페이징을 지원하는 오픈 라이선스 이미지 검색 API입니다.


