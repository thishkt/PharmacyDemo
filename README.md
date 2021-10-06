Android 口罩地圖入門實戰 30 天 (使用 Kotlin 程式語言)
===
> 本篇文章同步發表在 [HKT 線上教室](https://tw-hkt.blogspot.com/) 部落格，線上影音教學課程已上架至 [Udemy](http://bit.ly/2MFaEyE) 和 [Youtube](https://bit.ly/3nHWiuz) 頻道。另外，想追蹤更多相關技術資訊，歡迎到 [臉書粉絲專頁](https://www.facebook.com/hktblog/) 按讚追蹤喔～

![](https://i.imgur.com/6tQugyt.png)

前言
===

哈囉～大家好，我是 KT，這次報名參加第13屆【2021 iThome鐵人賽】Mobile Development 行動開發組，這次參賽主題，選擇曾經貼近你我身邊的實際例子：「口罩地圖」。

2020年初，爆發新型冠狀病毒肺炎(COVID-19)疫情，當時口罩搶破頭，面對一罩難求的情況下，政府管制口罩禁止出口，國內採實名制，民眾可持健保卡至藥局購買。在政府公開口罩庫存資料，透明呈現每間藥局即時庫存，大家不用再辛苦挨家挨戶的跑遍每間藥局詢問有無口罩，方便民眾購買到口罩，因此在當時誕生出了「口罩地圖」等相關服務應用程式。

![](https://i.imgur.com/MAYZAYf.png)

此圖片取自：[IT邦幫忙](https://www.facebook.com/ithelpfans/)


口罩地圖，已經過時了？
===
這個答案是肯定的，隨著口罩大量生產充足，人人皆有醫療口罩情況下，的確再也用不上手機裡面的口罩地圖，或許也不知多早以前就已經默默地從手機應用程式當中刪除。曾經多麼迫切的需要，但伴隨著時間，整個環境的變化。口罩地圖，已經過時了！

那爲什麼這次【2021 iThome鐵人賽】選題，要挑這個過時的主題呢？

或許我們再也用不上口罩地圖、但實際上這系列為大家準備的三十天的文章，我們學到的不會只是口罩地圖的開發，而是一套紮實 Android 開發應用程式的方法。透過手拉手，一步一步實際動手做這個「口罩地圖」，從入門開始，學習如何使用 Kotlin 程式語言，並且透過實際的應用，來串接真實的口罩即時庫存公開資料，從實作中來學習如何開發一款 Android APP 應用程式。文章內容包含了; 如何使用主流 OkHttp 第三方 Library 與 GSON 處理網路連線串接實際 API 應用、如何使用 TextView、Button、RecyclerView、Spinner、Toast、AlertDialog 等常用的 UI 元件，且進一步的在實戰中介紹進階 Kotlin 的 Collection 語法，如何靈活去運用，最後學習如何獲取使用者位置權限、判斷是否開啟 GPS、學習如何使用 Google Map 結合口罩資料，在地圖上，使用圖釘 Maker 與自定義客製化資訊視窗，顯示每間藥局名稱與口罩數量。

有了這套開發經驗，其實我們只要將資料來源轉換成是天氣，它就會是一款氣象APP；同理換成 YouBike 微笑單車，它就是可以隨時查詢當下每站租借單車狀態的 APP；如果是公車進站資訊，就可以變成等公車 APP，以此類推。

不問國家能為我們做什麼，只問我們自己能給大家帶來什麼，所以我希望可以留下這套開發經驗，繼續傳承分享下去，下次國家再需要我們的時候，我們已經準備好了，口罩國家隊。

大綱
===
目前預計 30 天準備挑戰的，根據內容分成了六個部分，整體規劃大綱，如下：

### Part 1. Go 準備開始

Day 1：[過時的 Android 口罩地圖 APP 應用程式 ?!](https://ithelp.ithome.com.tw/articles/10258929)

Day 2：[Kotlin 程式設計基礎入門 (1)](https://ithelp.ithome.com.tw/articles/10259341)

Day 3：[Kotlin 程式設計基礎入門 (2)](https://ithelp.ithome.com.tw/articles/10259743)

Day 4：[Kotlin 程式設計基礎入門 (3)](https://ithelp.ithome.com.tw/articles/10259778)

Day 5：[口罩即時庫存開放資料](https://ithelp.ithome.com.tw/articles/10260368)

Day 6：[建立口罩地圖APP專案](https://ithelp.ithome.com.tw/articles/10260619)

Day 7：[git 版本控制](https://ithelp.ithome.com.tw/articles/10260960)

### Part 2. 網路資料處理

Day 8：[OkHttp 獲取網路資料方式](https://ithelp.ithome.com.tw/articles/10261348)

Day 9：[JSON 資料解析](https://ithelp.ithome.com.tw/articles/10261711)

Day 10：[Gson 資料解析](https://ithelp.ithome.com.tw/articles/10262049)

Day 11：[ProgressBar 忙碌圈圈](https://ithelp.ithome.com.tw/articles/10262356)

Day 12：[封裝 OkHttp](https://ithelp.ithome.com.tw/articles/10262765)

### Part 3. UI 使用者介面元件 - RecyclerView 滾動式列表顯示口罩資料

Day 13：[RecyclerView 基本資料列表顯示](https://ithelp.ithome.com.tw/articles/10263176)

Day 14：[RecyclerView 進階項目佈局](https://ithelp.ithome.com.tw/articles/10263656)

Day 15：[RecyclerView 卡片式項目佈局](https://ithelp.ithome.com.tw/articles/10264141)

Day 16：[RecyclerView 跳頁&資料傳遞(1)](https://ithelp.ithome.com.tw/articles/10264909)

Day 17：[RecyclerView 跳頁&資料傳遞(2)](https://ithelp.ithome.com.tw/articles/10265804)


### Part 4. Kotlin 過濾＆分組語法介紹與應用

Day 18：[Kotlin 過濾(filter)集合資料用法](https://ithelp.ithome.com.tw/articles/10266615)

Day 19：[Kotlin 分組(groupBy)集合資料用法](https://ithelp.ithome.com.tw/articles/10267417)

Day 20：[縣市鄉鎮小工具包(util)](https://ithelp.ithome.com.tw/articles/10268123)

### Part 5. UI 使用者介面元件 - Spinner 下拉選單實戰應用

Day 21：[Spinner 下拉選單基本用法](https://ithelp.ithome.com.tw/articles/10268876)

Day 22：[Spinner 下拉選單結合縣市鄉鎮小工具](https://ithelp.ithome.com.tw/articles/10269553)

### Part 6. Google Map 地圖實戰應用

Day 23：[獲取位置權限](https://ithelp.ithome.com.tw/articles/10270281)

Day 24：[檢查GPS狀態](https://ithelp.ithome.com.tw/articles/10270980)

Day 25：[獲取位置經緯度](https://ithelp.ithome.com.tw/articles/10271669)

Day 26：[Google Map 範本學習(1)](https://ithelp.ithome.com.tw/articles/10272349)

Day 27：[Google Map 範本學習(2)](https://ithelp.ithome.com.tw/articles/10273032)

Day 28：[Google Map 顯示目前位置](https://ithelp.ithome.com.tw/articles/10273721)

Day 29：[Google Map 自訂資訊視窗](https://ithelp.ithome.com.tw/articles/10274390)

Day 30：[Google Map 結合口罩資料 ＆ 鐵人賽最後一天](https://ithelp.ithome.com.tw/articles/10275046)


主要 UI 畫面規劃 Wireframe
===
這 30 天的鐵人賽挑戰，將要實際自己動手來刻出以下這幾個畫面：

> Wireframe 指的是 UI 線框草稿圖，在還沒有實際定案的畫面出來且還在討論規劃整體流程與畫面階段時，可以先用簡單的線框拉出大概的版型，不用讓人滿腦問號去意會、憑空口說去想像畫面。目前業界主流常用的工具有：[Figma](https://www.figma.com/)、[Sketch](https://www.sketch.com/) 和 [Axure RP](https://www.axure.com/)。


## 藥局名稱列表頁 Wireframe
第一個主要 UI 畫面上，我們將從 APP 最常用的資訊列表清單開始，在清單中列出所有藥局名稱的可滾動式列表。將會介紹如何使用 Recyclerview 和 Adapter 且同時搭配政府公開資料呈現出全台灣所有藥局名稱。

![](https://i.imgur.com/LdP3cfe.png)

## 縣市、鄉鎮下拉選單與藥局詳細資訊頁的 Wireframe
有了基礎的滾動式列表建立經驗後，我們將進一步，在畫面新增兩個下拉選單，分別用來選擇縣市與鄉鎮區域，透過 Kotlin 的 Collection 語法，從全台數千家藥局，濾出用戶選擇區域的藥局資料，並且點擊列表中的項目，可以跳轉到該間藥局相關資訊詳細頁。



![](https://i.imgur.com/vfBhZpt.png)




## 口罩地圖 Wireframe
透過手機定位座標位置，在 Google Map 上透過圖釘的圖示顯示附近區域的藥局，點擊圖釘圖示會以卡片方式呈現該間藥局名稱、大人與小孩口罩數量。點擊卡片會跳轉到該間藥局相關資訊詳細頁。

![](https://i.imgur.com/dJoKqdX.png)


著作聲明
===
「Android 口罩地圖入門實戰 30 天 (使用 Kotlin 程式語言)」中提供所有的圖文、程式碼與影片教學，以下簡稱「本資料」。「本資料」，允許使用者重製、散布、傳輸以及修改著作，但不得為商業目的之使用。使用時必須註明來源出處: HKT 線上教室，HKT(侯光燦)。




![](https://i.imgur.com/cS98hwe.png)



商標內容聲明

「本資料」，其中所引用之各商標及產品名稱分屬其合法公司所有，「本資料」，部分採用開放源始碼、圖文與影音等多媒體，引用自於網路，皆屬於其原作者之所有，「本資料」引用純屬介紹之用，並無任何侵權之意，特此聲明，其中內容若有不妥，或是侵犯了您的合法權益，請麻煩通知我們，我們將會迅速協助將侵權的部分移除，謝謝!


參考資料
---
HKT 線上教室
https://tw-hkt.blogspot.com/

Freepik
https://www.freepik.com/

---
![](https://i.imgur.com/PL7FFgx.png)

那今天【iThome 鐵人賽】就介紹到這邊囉～ 

順帶一提，[KT 線上教室，臉書粉絲團](https://www.facebook.com/hktblog)，會不定期發佈相關資訊，不想錯過最新資訊，不要忘記來按讚，追蹤喔！也歡迎大家將這篇文章分享給更多人喔。

我們明天再見囉！！！掰掰～
