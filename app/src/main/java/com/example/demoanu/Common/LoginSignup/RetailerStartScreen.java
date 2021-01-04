package com.example.demoanu.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.example.demoanu.R;

public class RetailerStartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_start_screen);
    }
    public void CallLoginScreen(View  view){
       Intent intent = new Intent(getApplicationContext(),Login.class);
       Pair [] pairs = new Pair[1];
       pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transition_login");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RetailerStartScreen.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        }else
            {
                startActivity(intent);
            }

    }
    public void call_Signup_Screen(View  view){
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        Pair [] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.signup_btn),"transition_signup");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RetailerStartScreen.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        }else
        {
            startActivity(intent);
        }

    }
}