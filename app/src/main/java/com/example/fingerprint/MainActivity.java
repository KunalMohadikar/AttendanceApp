package com.example.fingerprint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;





public class MainActivity extends AppCompatActivity {

    private TextView mHeadingLabel;
    private ImageView mFingerprintImage;
    private TextView mParaLabel;
    int x = 0;

    class FingerprintHandler1 extends FingerprintManager.AuthenticationCallback {

        private Context context;

        public FingerprintHandler1(Context context) {
            this.context = context;
        }

        public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
            CancellationSignal cancellationSignal = new CancellationSignal();

            fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);

        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            this.update("There was a Auth Error, " + errString, false);
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            this.update("Auth Failed", false);
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            this.update("Error" + helpString,false);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            this.update("You can now Access the app",true);
            Intent i = new Intent("first_filter");
            startActivity(i);

        }

        private void update(String s, boolean b) {
            TextView paralabel =  (TextView) ((Activity)context).findViewById(R.id.paraLabel);
            ImageView imageView =  (ImageView) ((Activity)context).findViewById(R.id.fingerprintImage);

            paralabel.setText(s);
            if(b==false)
            {
                paralabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            else
            {
                paralabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                imageView.setImageResource(R.mipmap.action_done);


            }
        }
    }

    private FingerprintManager fingerprintManager;
    KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadingLabel = findViewById(R.id.headingLabel);
        mFingerprintImage = findViewById(R.id.fingerprintImage);
        mParaLabel = findViewById(R.id.paraLabel);

        // TODO 1: Android version should be greater or equal to marshmelow
        // TODO 2: Device has fingerprint scanner
        // TODO 3: Have permission to use fingerprint scanner in the app
        // TODO 4: LockScreen is secured with at least 1 type of lock
        // TODO 5: Atleast 1 fingerprint is registered

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected())
            {
                mParaLabel.setText("Fingerprint scanner not detected on this device");
            }
            else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            {
                mParaLabel.setText("Permission denied for Fingerprint Scanner");
            }
            else if(!keyguardManager.isKeyguardSecure())
            {
                mParaLabel.setText("Add Lock to your phone settings");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints())
            {
                mParaLabel.setText("You should add at least one fingerprint to use this feature");
            } else
            {
                mParaLabel.setText("Place your finger on Scanner to access the app");

                FingerprintHandler1 fingerprintHandler = new FingerprintHandler1(this);
                fingerprintHandler.startAuth(fingerprintManager,null);

            }


        }
    }
}
