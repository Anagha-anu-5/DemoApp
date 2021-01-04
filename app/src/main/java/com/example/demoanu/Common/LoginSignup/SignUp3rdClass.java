package com.example.demoanu.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.example.demoanu.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3rdClass extends AppCompatActivity {
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);
        phoneNumber = findViewById(R.id.signup_phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);
        scrollView = findViewById(R.id.signup_3rd_screen_view);
    }

    public void callVerifyOTPScreen(View view) {
        //validate fields
        if (!validatePhoneNumber()) {
            return;
        }//validation succeeded and now move to next Screen to verify phone number and save data
        String _fullName = getIntent().getStringExtra("fullName");
        String _username = getIntent().getStringExtra("username");
        String _email = getIntent().getStringExtra("email");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        Intent intent1 = new Intent(getApplicationContext(), VerifyOTP.class);

        // pass All fields to next activity
        intent1.putExtra("fullName", _fullName);
        intent1.putExtra("username", _username);
        intent1.putExtra("email", _email);
        intent1.putExtra("password", _password);
        intent1.putExtra("date", _date);
        intent1.putExtra("gender", _gender);
        intent1.putExtra("phoneNo", _phoneNo);

        //Add Transition

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
            startActivity(intent1, activityOptions.toBundle());
        } else {
            startActivity(intent1);
        }


    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkSpaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
//        } else if (!val.matches(checkSpaces)) {
//            phoneNumber.setError("No White spaces are allowed!");
//            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

}