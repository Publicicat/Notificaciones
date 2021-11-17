package com.publicicat.pgram;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdMessagingService extends FirebaseMessagingService {

    //CREEM AQUESTA NOVA CLASE Id PER SEPARAT
    //TOT I QUE A LA DOCUMENTACIÓ A GITHUB ESTIGUI TOT JUNT
    //PERQUÈ LA DIFERÈNCIA ESTÀ EN QUÈ AL MANIFEST LI APLICAREM
    //UNA ACCIÓ DIFERENT: com.google.firebase.INSTANCE_ID_EVENT
    private static final String TAG = "FIREBASE_TOKEN";

    // [START on_new_token]
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any
     * server-side account maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}

