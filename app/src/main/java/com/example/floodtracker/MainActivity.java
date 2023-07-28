package com.example.floodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    private int sign_in_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);



        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            // null : the user have not sign in yet

            Toast.makeText(this, "Please sign in with Gmail account" , Toast.LENGTH_SHORT).show();

        } else{
            // user already sign in, show the next page
            Intent intent = new Intent(this, com.example.floodtracker.HomePage.class);
            intent.putExtra("Name" , account.getDisplayName());
            intent.putExtra("Email" , account.getEmail());

            startActivity(intent);
        }


    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            // Clicked the Google Sign-In button, start the Google Sign-In flow
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 10);
        }
        // Add other cases here if needed
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 10) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //open a new activity
            Toast.makeText(this,"already signed in", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, com.example.floodtracker.HomePage.class);
            intent.putExtra("Name" , account.getDisplayName());
            intent.putExtra("Email" , account.getEmail());

            startActivity(intent);


        } catch (ApiException e) {

            //cannot sign in
            Toast.makeText(this, "You cannot be Signed in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, com.example.floodtracker.HomePage.class);
            //updateUI(null);
        }
    }


}