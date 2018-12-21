package com.hyxen.adlocussample;

import android.text.TextUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hyxen.adlocusaar.AdLocus;
import com.hyxen.adlocusaar.constants.Constants;
import com.hyxen.adlocusaar.repository.data.response.GetFCMDataResponse;
import com.hyxen.adlocusaar.utils.Logger;

import java.util.Map;

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
