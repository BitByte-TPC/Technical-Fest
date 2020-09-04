package com.priyanshnama.technical_fest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class EventInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        String event_name = Objects.requireNonNull(getIntent().getExtras()).getString("event_name");
        TextView txt_event_name = findViewById(R.id.event_name);
        txt_event_name.setText(event_name);
    }
}