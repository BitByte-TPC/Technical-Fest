package com.priyanshnama.technical_fest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class RulesActivity extends AppCompatActivity {
    private String rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        TextView text = findViewById(R.id.textView);
        rules = Objects.requireNonNull(getIntent().getExtras()).getString("rules");
        text.setText(rules);
    }
}