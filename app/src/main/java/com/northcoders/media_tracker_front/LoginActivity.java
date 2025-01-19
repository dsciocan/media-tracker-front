package com.northcoders.media_tracker_front;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Arrays;
import java.util.List;

/**
 *   It seems like this class (GoogleSignIn)has been deprecated but for the sake of getting something that works, this works
 *   There probably is an updated version of doing all of this
 *   If you do log in with google on the Phone -> probably use the TeamDurocJava@gmail.com email
 */
public class LoginActivity extends AppCompatActivity {
    private LoginActivityViewModel viewModel;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        viewModel =  new ViewModelProvider(this).get(LoginActivityViewModel.class);

        // Setting up the logic for the sign in button
        Button signInButton = findViewById(R.id.button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Choose authentication providers -> Just google currently -> Can "easily" add others
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        //new AuthUI.IdpConfig.EmailBuilder().build(),
                        //new AuthUI.IdpConfig.PhoneBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                        //new AuthUI.IdpConfig.FacebookBuilder().build(),
                        //new AuthUI.IdpConfig.TwitterBuilder().build()
                        );

                // Create and launch sign-in intent
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build();

                signInLauncher.launch(signInIntent);
            }
        });
    }

    // If the user opens up the app this checks if they've already logged in before (I think(?))
    // and will put the user on the MainActivity page
    @Override
    public void onStart(){
        super.onStart();

        /**
         * todo:
         *  add some sort of verification to not allow the app to continue to the main page without
         *  having the backend give the ok
         */
        // See if there is a user (if they've already signed in before) if so, let them start on main
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Log.i("Firebase Login Page","User is already logged in: " + currentUser.getEmail());
            // Now we can start a new intent to move to the MainActivity
            Intent intent = new Intent(this, com.northcoders.media_tracker_front.MainActivity.class);

            // This actually starts the move to the MainActivity
            startActivity(intent);
        }
        else{
            Log.i("Firebase Login Page","User is not signed in");
        }
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );




    private void onSignInResult(FirebaseAuthUIAuthenticationResult result)  {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in

            // The getAuth() is just a placeholder method to debug sending tokens to the back
            viewModel.getAuth();

            // This was for sending the token for the backend for testing getCurrentUser();
            // Will likely be deleted
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Task<GetTokenResult> task = user.getIdToken(true);

            // Once those are obtained now we can start a new intent to move to the MainActivity
            Intent intent = new Intent(this, com.northcoders.media_tracker_front.MainActivity.class);

            // This actually starts the move to the MainActivity
            startActivity(intent);

        } else {
            Log.i("FIREBASE LOGIN L_ACTIVITY",result.toString());
            Log.i("FIREBASE LOGIN L_ACTIVITY",response.getError().toString());
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            // Maybe put a Toast(?)
        }
    }

}
