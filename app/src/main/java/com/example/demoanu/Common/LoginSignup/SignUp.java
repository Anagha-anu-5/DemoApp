package com.example.demoanu.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoanu.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {
    ImageView backBtn;
    Button next, login;
    TextView titleText;
    TextInputLayout fullName, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_sign_up);
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);

        fullName = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }

    public void callNextSignupScreen(View view) {

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);
        intent.putExtra("fullName",fullName.getEditText().getText().toString().trim());
        intent.putExtra("username",username.getEditText().getText().toString().trim());
        intent.putExtra("email",email.getEditText().getText().toString().trim());
        intent.putExtra("password",password.getEditText().getText().toString().trim());

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }

    }

    /*Validation Functions */
    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();  //trim() means it do not store extra space in DB
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);  // removes extra space allocated for error
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();  //trim() means it do not store extra space in DB
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No White spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);  // removes extra space allocated for error
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();  //trim() means it do not store extra space in DB
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);  // removes extra space allocated for error
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();  //trim() means it do not store extra space in DB
        String checkPassword = "anu"; //"^" +
        //"(?=.*[0-9])" +         //at least 1 digit
        //"(?=.*[a-z])" +         //at least 1 lower case letter
        //"(?=.*[A-Z])" +         //at least 1 upper case letter
        //"(?=.*[a-zA-Z])" ;      //any letter
        //"(?=.*[@#$%^&+=])" +    //at least 1 special character
        //"(?=S+$)"+          //no white spaces
        //".{4,}" +               //at least 4 characters
        //"$";
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);  // removes extra space allocated for error
            return true;
        }
    }
}