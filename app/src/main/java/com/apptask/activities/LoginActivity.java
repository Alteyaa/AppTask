package com.apptask.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apptask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.view.View.GONE;

public class LoginActivity extends AppCompatActivity {

    EditText editPhone,editCode;
    boolean codeSent;
    String verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private View viewPhone,viewCode;

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editPhone = findViewById(R.id.etPhone);
        editCode = findViewById(R.id.etCode);
        viewPhone = findViewById(R.id.viewPhone);
        viewCode = findViewById(R.id.viewCode);
        viewCode.setVisibility(GONE);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                if (codeSent) {
                    String code = phoneAuthCredential.getSmsCode();
                    editCode.setText(code);
                } else {
                    signIn(phoneAuthCredential);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginActivity.this,"OnVerificationFailed" + e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                codeSent = true;
                verificationId = s;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {

            }
        };
    }

    public void onClickSend(View view) {
        viewPhone.setVisibility(GONE);
        viewCode.setVisibility(View.VISIBLE);
        String phone ="+996"+ editPhone.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,               // Phone number to verify
                60,              // Timeout duration
                TimeUnit.SECONDS,  // Unit of timeout
                this,     // Activity (for callback binding)
                callbacks);

    }
    public void onClickConfirm(View view) {
        String code = editCode.getText().toString().trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signIn(credential);

    }
    private void signIn (PhoneAuthCredential phoneAuthCredential){
        FirebaseAuth.getInstance().
                signInWithCredential(phoneAuthCredential).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
                            preferences.edit().putBoolean("ifRegistered",true).apply();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this,"Vy avtorizovany",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

}
