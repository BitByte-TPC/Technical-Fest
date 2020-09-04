package com.priyanshnama.technical_fest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.Objects;

public class EventInfoActivity extends AppCompatActivity {
    private Button participate;
    private ImageButton email, call;
    private TextView name, desc, rules, prize, person;
    private ImageView image;
    private String emailId, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        String event_name = Objects.requireNonNull(getIntent().getExtras()).getString("event_name");

        participate = findViewById(R.id.participate);
        email = findViewById(R.id.email);
        call = findViewById(R.id.call);
        name = findViewById(R.id.event_name);
        desc = findViewById(R.id.desc);
        rules = findViewById(R.id.rules);
        prize = findViewById(R.id.prize);
        image = findViewById(R.id.event_image);
        person = findViewById(R.id.person);

        findViewById(R.id.back).setOnClickListener(v -> finish());
        name.setText(event_name);
        getData(event_name);
    }

    private void getData(String event_name) {
        FirebaseFirestore.getInstance().collection("Events")
                .document(event_name)
                .get().addOnCompleteListener(task -> {
                    if(task.getResult().exists()) {
                        Map<String, Object> data = task.getResult().getData();
                        assert data != null;

                        person.setText(Objects.requireNonNull(data.get("contact")).toString());
                        emailId = Objects.requireNonNull(data.get("email")).toString();
                        Picasso.get().load(Objects.requireNonNull(data.get("image")).toString()).into(image);
                        phone = Objects.requireNonNull(data.get("phone")).toString();
                        desc.setText(Objects.requireNonNull(data.get("desc")).toString());
                        rules.setText(Objects.requireNonNull(data.get("rules")).toString());
                        prize.setText(Objects.requireNonNull(data.get("prize")).toString());
                        rules.setText(Objects.requireNonNull(data.get("rules")).toString());
                    }
                });
    }
}