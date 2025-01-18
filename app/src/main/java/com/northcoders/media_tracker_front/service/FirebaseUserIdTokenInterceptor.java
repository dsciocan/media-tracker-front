package com.northcoders.media_tracker_front.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor class that will intercept all HTTP requests for the RetroFitInstance for the UserActionsService
 * This prevents the user having to constantly log in when trying to do something specific for the user (I hope)
 * And is a single place to add headers without changing each of UserActionsService requests
 */
public class FirebaseUserIdTokenInterceptor implements Interceptor {

    // Custom header for the http request (can be anything but has to be the same on the backend
    private static final String X_FIREBASE_ID_TOKEN = "Authorization";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        // The Log's were used for debugging

        Request request = chain.request();
        Log.i("TOKEN_INTERCEPTOR", "WE MADE IT TO THE INTERCEPTOR");
        // Try to get the current user
        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user == null){
                // If there's no user there's no header
                Log.i("TOKEN_INTERCEPTOR", "NO USER IN THE INTERCEPTOR");
                return chain.proceed(request); // Has no auth header
            }
            else{
                // Get the token for the user if there is one
                // This being set to true allows the token to be refreshed by Firebase so there's no need for the backend to do it(?)
                Task<GetTokenResult> task = user.getIdToken(true);

                // Timeout 10 Seconds (unsure why -> found on StackOverFlow)
                GetTokenResult tokenResult = Tasks.await(task,10, TimeUnit.SECONDS);

                String idToken = tokenResult.getToken();

                if(idToken == null) {
                    Log.i("TOKEN_INTERCEPTOR", "NO TOKEN IN THE INTERCEPTOR");
                    return chain.proceed(request); // Has no auth header
                }

                // All checks cleared -> Token is added to the request in the header
                Log.i("TOKEN_INTERCEPTOR", "WE MADE IT THROUGH THE INTERCEPTOR");
                return chain.proceed(request.newBuilder()
                        .addHeader(X_FIREBASE_ID_TOKEN, "Bearer " + idToken) // Has auth header
                        .build());
            }

        }catch(Exception e){
            // Logging any exceptions caught
            Log.e("INTERCEPTOR TOKENS", "ERROR IN THE INTERCEPTOR");
            Log.e("INTERCEPTOR TOKENS", e.toString());

            // Will just do the request without a header
            return chain.proceed(request); // Has no auth header
        }

    }
}
