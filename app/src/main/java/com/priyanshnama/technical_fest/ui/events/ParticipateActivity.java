package com.priyanshnama.technical_fest.ui.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.priyanshnama.technical_fest.R;

import java.util.Objects;

public class ParticipateActivity extends AppCompatActivity {
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participate);
        TextView text = findViewById(R.id.textView);
        name = Objects.requireNonNull(getIntent().getExtras()).getString("name");
        text.setText(name);
    }
}