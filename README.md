# Setup AdLocus SDK and FCM Sample


* [1.註冊登入Firebase](#1)
* [2.設定Android studio](#2)
* [3.取得 FCM Key](#3)
* [4.在Android專案新增接收FCM Servcie](#4)
* [5.設定AdLocus SDK](#5)



<h1 id="1">1.註冊登入Firebase</h1>

網址:[https://firebase.google.com/](https://firebase.google.com/)

到 firebase 註冊登入 gmail 帳號

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545363464226.jpg)

<br>


![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545363541302.jpg)

<h1 id="2">2.設定Android studio</h1>

開啟 android studio 的工具列,

在 Tool 選項中找到 Firebase 的選項,

點選後出現側欄,

找到Cloud Messaging

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545371903122.jpg)


<br>

點擊 Connect to Firebase 會出現授權

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545373275001.jpg)

<br>

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/05.png)

<br>

選擇要連結或開新的 Project

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545372651264.jpg)

<br>

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/06.png)

<br>

回到 Android Studio 會看到 Project 連結成功的畫面
接著按下第二個按鈕 Add FCM to your app

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545373890606.jpg)

<br>

你就會看到我們左邊側欄出現了一個 google-services.json 的檔案被放置在 app 資料夾底下

如果沒看到記得把 Android 模式切換到 Project 模式

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545374783065.jpg)

<br>

也會看到 build.gradle 自動加入了 classpath的程式碼

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545374908534.jpg)

<br>

最後打開 Firebase 的頁面,會發現新專案被加入到 Firebase 了

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545375156275.jpg)

<h1 id="3">3.取得 FCM Key</h1>

在上圖firebase頁面,點進專案,

找到左上角 設定 -> 專案設定 -> Cloud Message

可找到伺服器金鑰

![image](https://github.com/vfhhu/AdLocusSample/blob/master/images/1545375788357.jpg)

<br>

<h1 id="4">4.在Android專案新增接收FCM Servcie</h1>

新增 MyFirebaseInstanceIDService 與 MyFirebaseMessagingService
並設定 manifests

```code
<!-- FCM -->
<service android:name=".MyFirebaseInstanceIDService">
   <intent-filter>
       <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
   </intent-filter>
</service>

<service android:name=".MyFirebaseMessagingService">
   <intent-filter>
       <action android:name="com.google.firebase.MESSAGING_EVENT" />
   </intent-filter>
</service>
```
```java
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private final static String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Logger.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        //save and update token to server
        AdLocus.getInstance().updatePushToken(token);
    }
}
```
```java
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Logger.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Logger.d(TAG, "Message data payload: " + remoteMessage.getData());
            if (TextUtils.equals(data.get(GetFCMDataResponse.TAG_TARGET), Constants.TAG_FCM_TARGET))
                AdLocus.getInstance().sendFCMMessage(this,data);
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
            } else {
                // Handle message within 10 seconds
//                handleNow();
            }
        }
    }
}
```

<h1 id="5">5.設定AdLocus SDK</h1>

參照:[AdLocus](https://docs.google.com/document/d/1HsQnsslmAM1g1-FbbEDrWP3NyhN1sRGZ3OPZN0nH0rg/edit)文檔



<!-- [step3-2]:data:image/png;base64,iVBORw0...... -->
<!-- ![avatar](data:image/png;base64,iVBORw0......) -->