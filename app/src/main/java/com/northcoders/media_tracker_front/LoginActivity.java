package com.northcoders.media_tracker_front;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 *   It seems like this class (GoogleSignIn)has been deprecated but for the sake of getting something that works, this works
 *   There probably is an updated version of doing all of this
 *   If you do log in with google on the Phone -> probably use the TeamDurocJava@gmail.com email
 */
public class LoginActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setting up the Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Setting this activity as the one to "manage the lifecycle of the GoogleSignInClient"
        googleSignInClient = GoogleSignIn.getClient(this,gso);


        // Setting up the logic for the sign in button
        SignInButton signInButton = findViewById(R.id.sign_in_google_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the "intent"(fancy way of saying an "action"(?))
                Intent signInIntent = googleSignInClient.getSignInIntent();

                // Starts the sign in process
                // Deprecated method but can still be used(?)
                // The parameters are: (Intent intent, int requestCode)
                // It successfully moves on if the requestCode is >=0 (I'm unsure of this?)
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // I'm assuming onActivityResult looks for requestCodes to determine what part of the
        // activity is doing something (eg: the sign in with google button wants to do an oauth sign in with google)
        // and what to do with them(?)

        // The request code would be equal to what we sent in the startActivityForResult
        // And now we can decide what to do
        if(requestCode == RC_SIGN_IN){
            // This is the persons data from obtained from their sign in(?)
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            // Now we decide what to do with it
            handleSignInResult(task);
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){

        try{
            // Trying to get the result from the "Task" data
            // If there's a problem doing that it throws an ApiException
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Now we have the account we can decide what to do with that data
            updateUI(account);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateUI(GoogleSignInAccount account){
        // If there is an account then we can get things such as the email address, display name
        // and the uri for their profile picture
        //      -> (For the TeamDuroc account there is no picture so that is why it is most likely null)
        if (account != null){
            Log.i("G_LOG",account.getEmail()+" "+ account.getDisplayName());
            Log.i("G_LOG",String.valueOf(account.getPhotoUrl()));
           String email = account.getEmail();
            String name  =account.getDisplayName();
            String picture  = String.valueOf(account.getPhotoUrl());

            // Once those are obtained now we can start a new intent to move to the MainActivity
            Intent intent = new Intent(this, com.northcoders.media_tracker_front.MainActivity.class);

            // We can also move pieces of data to the other activity if we wanted to
            intent.putExtra("email",email);
            intent.putExtra("name",name);
            intent.putExtra("picture",picture);

            // This actually starts the move from to the MainActivity
            // For an unknown reason this didn't work originally
            // -> The solution was to add the main activity to the manifest
            startActivity(intent);

        }
    }

}
