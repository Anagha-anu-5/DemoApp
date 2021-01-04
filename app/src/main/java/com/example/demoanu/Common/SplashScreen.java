package com.example.demoanu.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoanu.R;
import com.example.demoanu.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER =5000;  //5 sec

    ImageView  backgroundImage;
    TextView poweredByLine;
    SharedPreferences onBoardingScreen;

    //Animations
    Animation sideAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To Remove Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        //Hooks
        backgroundImage =(ImageView)findViewById(R.id.img_splash);
        poweredByLine =(TextView) findViewById(R.id.powered_by_line);
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //set Animations on elements
        backgroundImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {   //which will delayed something inside the function
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("FirstTime",true);
                if(isFirstTime)
                {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("FirstTime",false);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                    finish();                           // which will remove instance of previous Activity

                }else
                    {
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        finish();
                    }
                           }
        },SPLASH_TIMER);
    }
}
