package com.andoid.connect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {

    private static final String TAG = "";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private BaseActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        baseActivity = new BaseActivity(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onguidebook(View view) {
        Intent i1 = new Intent(this, GuidebookMain.class);
        startActivity(i1);
    }

    public void onProfile(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) { Intent i2 = new Intent(MainMenu.this, ProfileMainPage.class);
            startActivity(i2);
            // User is signed in
        } else { Intent i3 = new Intent(MainMenu.this, LoginPage.class);
            startActivity(i3);
            // No user is signed in
        }

    }

    public void onSchedules(View view){
        Intent i4 = new Intent(this, Schedules.class);
        startActivity(i4);
    }

    public void onWeather(View view) {
        if (getPackageManager().getLaunchIntentForPackage("com.samsung.android.weather") != null) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.samsung.android.weather");
        startActivity(intent);
        // 설치됨
    } else {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://play.google.com/store/search?q=weather&c=apps"));
        startActivity(intent);
        // 미설치
    }

    }

    public void onTranslator(View view) {
        if (getPackageManager().getLaunchIntentForPackage("com.hancom.interfree.genietalk") != null) {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.hancom.interfree.genietalk");
            startActivity(intent);
            // 설치됨
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.hancom.interfree.genietalk"));
            startActivity(intent);
            // 미설치
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        baseActivity.onBackPressed();
        mAuth.signOut();
    }

}

