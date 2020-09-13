package com.priyanshnama.technical_fest.ui.options;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.priyanshnama.technical_fest.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContactUsActivity extends AppCompatActivity {

    private EditText txt_phone, txt_subject, txt_message;
    private String name, email, phone, subject, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        
        txt_phone = findViewById(R.id.phone);
        txt_subject = findViewById(R.id.subject);
        txt_message = findViewById(R.id.message);

        findViewById(R.id.send).setOnClickListener(this::sendMessage);
        findViewById(R.id.back).setOnClickListener(v -> finish());
        findViewById(R.id.facebook).setOnClickListener(v -> open(R.string.facebook));
        findViewById(R.id.instagram).setOnClickListener(v -> open(R.string.instagram));
        findViewById(R.id.website).setOnClickListener(v -> open(R.string.website));
    }

    private void open(int id) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(id))));}

    private void sendMessage(View view) {
        phone = txt_phone.getText().toString();
        subject = txt_subject.getText().toString();
        message = txt_message.getText().toString();

        if(phone.equals(""))showSoftKeyboard(txt_phone);
        else if(subject.equals(""))showSoftKeyboard(txt_subject);
        else if(message.equals(""))showSoftKeyboard(txt_message);
        else{
            email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
            name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            send_submission();
        }
    }

    private void send_submission() {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("name", name);
        data.put("phone",phone);
        data.put("subject", subject);
        data.put("message",message);
        FirebaseFirestore.getInstance().collection("Contacts")
                .add(data)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(ContactUsActivity.this,"Thanks for Contacting US",Toast.LENGTH_LONG)
                                .show());
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}