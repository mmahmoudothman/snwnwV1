package com.snwnw.snwnw.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.snwnw.snwnw.R;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.user_model;
import com.snwnw.snwnw.presentation.presenters.impl.RegisterPresenter;
import com.snwnw.snwnw.presentation.presenters.interfaces.LoginPresenterListener;
import com.snwnw.snwnw.presentation.utils.Constants;
import com.snwnw.snwnw.presentation.utils.SNWNWPrefs;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fifi elshafie on 12/31/2017.
 */

public class MainActivity extends Activity implements LoginPresenterListener {
    @BindView(R.id.go)
    TextView go ;
    @BindView(R.id.register_btn)
    Button register_btn;
    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    @BindView(R.id.facebook_btn)
    Button facebook_btn;
    LoginButton loginButton ;
    @BindView(R.id.google_btn)
    SignInButton google_btn;
    @BindView(R.id.sign_in_button)
    SignInButton sign_in_button;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    public String TAG ="x";

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      //  updateUI(currentUser);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId("331504747646550");
//        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try {
            Log.e("Tokenis", FirebaseInstanceId.getInstance().getToken());
        }
        catch (Exception e) {

        }
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin();
            }
        });
// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setUpFB();
        AuthonticateGmailLogin();
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegister();
            }
        });

        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 loginButton.performClick();
            }
        });


        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmailLogin();
            }
        });
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gmailLogin();
             //   sign_in_button.performClick();
                gmailLogin();
            }
        });
    }


    public void goLogin(){

        Intent go_intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(go_intent);
        finish();
    }
    public void goRegister() {
        Intent intent = new Intent(MainActivity.this, registerActivity.class);
        startActivity(intent);
        finish();
    }
    public void setUpFB (){
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("google", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("google", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("google", "facebook:onError", error);
                // ...
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }
            catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("gooogleteees", "Google sign in failed", e);
                // ...
            }
            catch (Exception e){

                Log.i("amtheex",e.getMessage());
            }
        }
        else {
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("google", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("google", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i("giigle",user.getDisplayName());
                            HashMap <String,Object> socialParams = new HashMap<>();
                            socialParams.put("first_name",user.getDisplayName());
                            socialParams.put("last_name","");
                            socialParams.put("email",user.getEmail());
                            socialParams.put("image",user.getPhotoUrl());
                            socialParams.put("provider_id","181635512441204");
                            socialParams.put("provider","facebook");
                            socialParams.put("register_id", FirebaseInstanceId.getInstance().getToken());
                            socialParams.put("device_id","858323v3rrr22ff323");
                            socialParams.put("gender","");
                            socialParams.put("mobile",user.getPhoneNumber());
                            socialParams.put("platform","android");
                            socialParams.put("version",0) ;

                            Log.i("iamsocial",socialParams+"");

                            RegisterPresenter presenter = new RegisterPresenter(MainActivity.this, MainActivity.this);
                            presenter.registerSocial(socialParams);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("google", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void socialLogin(){

    }

    @Override
    public void onRegisterSuccess(String Token) {
        saveUser(Token);
        Intent go = new Intent(MainActivity.this,testAcitivty.class);
        startActivity(go);
        finish();
    }

    @Override
    public void onRegistrFailed(String Error) {
        Toast.makeText(getApplicationContext(),Error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(user_model user_model) {

    }

    @Override
    public void onLoginFailed(String Error) {

    }

    @Override
    public void onForgetPassSuccess(String Msg) {

    }

    @Override
    public void onForgetPassFail(String Msg) {

    }

    @Override
    public void onHomeCountriesLoadedSuccess(homeCountriesModel model) {

    }

    @Override
    public void onHomeCountriesLoadedFailed(String error) {

    }

    @Override
    public void onHomeCitiesLoadedSuccess(homeCitiesModel model) {

    }

    @Override
    public void onHomeCitiesLoadedFailed(String error) {

    }

    public void saveUser(String Token) {
        SNWNWPrefs prefs = new SNWNWPrefs();
        prefs.setDefaults(Constants.Token, Token, getApplicationContext());


    }


    public void AuthonticateGmailLogin(){
        // Configure Google Sign In
        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

      //  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
    public void gmailLogin(){
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i("iamgmail",user.getDisplayName());

                            HashMap <String,Object> socialParams = new HashMap<>();
                            socialParams.put("first_name",user.getDisplayName());
                            socialParams.put("last_name","");
                            socialParams.put("email",user.getEmail());
                            socialParams.put("image",user.getPhotoUrl());
                            socialParams.put("provider_id","181635512441204");
                            socialParams.put("provider","gmail");
                            socialParams.put("register_id", FirebaseInstanceId.getInstance().getToken());
                            socialParams.put("device_id","858323v3rrr22ff323");
                            socialParams.put("gender","");
                            socialParams.put("mobile",user.getPhoneNumber());
                            socialParams.put("platform","android");
                            socialParams.put("version",0) ;

                            Log.i("google",socialParams+"");

                            RegisterPresenter presenter = new RegisterPresenter(MainActivity.this, MainActivity.this);
                            presenter.registerSocial(socialParams);




                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void googleSignIn(View view) {
        gmailLogin();
    }
}
