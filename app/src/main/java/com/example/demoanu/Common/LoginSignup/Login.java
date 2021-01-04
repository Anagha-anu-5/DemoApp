package com.example.demoanu.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.demoanu.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueException;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;
import com.truecaller.android.sdk.clients.VerificationCallback;
import com.truecaller.android.sdk.clients.VerificationDataBundle;

public class Login extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;
    String _phoneNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);

        //TrueCaller sdk
        TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(this, sdkCallback)
                .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
                .buttonColor(Color.parseColor("#F5F5F5"))
                .buttonTextColor(Color.parseColor("#000000"))
                .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
                .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
                .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                .privacyPolicyUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .termsOfServiceUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .footerType(TruecallerSdkScope.FOOTER_TYPE_SKIP)
                .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
                .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITH_OTP)
                .build();

        TruecallerSDK.init(trueScope);

        (findViewById(R.id.login_with_truecaller)).setOnClickListener((View v) -> {

            //check if Truecaller SDk is usable
            if (TruecallerSDK.getInstance().isUsable()) {
                TruecallerSDK.getInstance().getUserProfile(Login.this);
            } else {
//                TruecallerSDK.getInstance().requestVerification("IN",phoneNumber.getEditText().getText().toString(),apiCallback,this);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("Truecaller App not installed.");

                dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                            Log.d("Login Activity", "onClick: Closing dialog");

                            dialog.dismiss();
                        }
                );

                dialogBuilder.setIcon(R.drawable.com_truecaller_icon);
                dialogBuilder.setTitle("Alert");

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void letTheUserLoggedin(View view) {
        if (!isConnected(this)) {
            showCustomDialog();
        }
        //Validate username and password
        if (!validateFields()) {
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        //get data
        _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        final String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        //Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);
                    String systemPassword = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);

                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullName = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _dateOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);

                        Toast.makeText(Login.this, _fullName + "\n" + _email + "\n" + _phoneNo + "\n" + _dateOfBirth, Toast.LENGTH_SHORT).show();
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Data does not exist!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /* Check Internet Connection  */

    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please Connect to Internet to proceed Further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), RetailerStartScreen.class));
                        finish();
                    }
                });

    }


    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number can not be empty");
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError("Password can not be empty");
            password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TruecallerSDK.getInstance().onActivityResultObtained(this, resultCode, data);

    }

    // Creating Instance for ITrueCallback class
    private final ITrueCallback sdkCallback = new ITrueCallback() {

        @Override
        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {
            Log.d("profile success", "Verified without OTP! (Truecaller User): " + trueProfile.firstName);
        }

        @Override
        public void onFailureProfileShared(@NonNull final TrueError trueError) {
            Log.d("profile error", "onFailureProfileShared: " + trueError.getErrorType());
        }

        @Override
        public void onVerificationRequired(@Nullable final TrueError trueError) {
//            TrueProfile profile = new TrueProfile.Builder(firstName, lastName).build();
            TruecallerSDK.getInstance().requestVerification("IN", phoneNumber.getEditText().getText().toString(), apiCallback, Login.this);
//            TruecallerSDK.getInstance().verifyMissedCall(profile, apiCallback);
        }

    };


    // Creating Instance for  apicallback
    static final VerificationCallback apiCallback = new VerificationCallback() {

        @Override
        public void onRequestSuccess(int requestCode, @Nullable VerificationDataBundle extras) {

            if (requestCode == VerificationCallback.TYPE_MISSED_CALL_INITIATED) {
                if (extras != null)
                    extras.getString(VerificationDataBundle.KEY_TTL);
            }

            if (requestCode == VerificationCallback.TYPE_MISSED_CALL_RECEIVED) {
                Log.d("", "Missed Call Triggered");
            } else {
                // This method is invoked when the user has been successfully verified using the missed call flow
                // along with providing his first name and last name and is verified successfully by the SDK
                Log.d("", "Verified Successfully");
            }

            if (requestCode == VerificationCallback.TYPE_OTP_INITIATED) {
                if (extras != null)
                    extras.getString(VerificationDataBundle.KEY_TTL);
            }

            if (requestCode == VerificationCallback.TYPE_OTP_RECEIVED) {
            }

            if (requestCode == VerificationCallback.TYPE_VERIFICATION_COMPLETE) {
                extras.getString(VerificationDataBundle.KEY_ACCESS_TOKEN);
            }

            if (requestCode == VerificationCallback.TYPE_PROFILE_VERIFIED_BEFORE) {
            }

        }

        @Override
        public void onRequestFailure(final int requestCode, @NonNull final TrueException e) {
            Log.d("Login Failed", "OnFailureApiCallback: " + e.getExceptionMessage());
        }

    };

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TruecallerSDK.clear();
    }
}