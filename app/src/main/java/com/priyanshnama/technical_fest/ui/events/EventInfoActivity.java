package com.priyanshnama.technical_fest.ui.events;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.priyanshnama.technical_fest.R;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.Objects;

public class EventInfoActivity extends AppCompatActivity {
    private TextView desc, prize, person, date, time, venue;
    private RecyclerView rules;
    private ImageView image;
    private String emailId, phone, event_name;
    private View event_info;
    private ProgressBar progressBar;
    private View rules_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        event_name = Objects.requireNonNull(getIntent().getExtras()).getString("event_name");

        TextView name = findViewById(R.id.event_name);
        event_info = findViewById(R.id.event_info);
        progressBar = findViewById(R.id.progressBar);
        desc = findViewById(R.id.desc);
        prize = findViewById(R.id.prize);
        image = findViewById(R.id.event_image);
        person = findViewById(R.id.person);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        venue = findViewById(R.id.venue);
        rules = findViewById(R.id.rules);
        rules.setLayoutManager(new LinearLayoutManager(this));
        rules_layout = findViewById(R.id.rules_layout);

        findViewById(R.id.back).setOnClickListener(this::back);
        findViewById(R.id.show_rules).setOnClickListener(this::showRules);
        findViewById(R.id.participate).setOnClickListener(this::participate);
        findViewById(R.id.email).setOnClickListener(this::sendEmail);
        findViewById(R.id.call).setOnClickListener(this::call);
        name.setText(event_name);
        getData(event_name);
    }

    private void back(View view) {
         if(rules_layout.getVisibility()==View.VISIBLE) {
             event_info.setVisibility(View.VISIBLE);
             rules_layout.setVisibility(View.INVISIBLE);
         }
         else
             finish();
    }

    private void participate(View view) {
        Intent intent = new Intent(this, ParticipateActivity.class);
        intent.putExtra("name",event_name);
        startActivity(intent);
    }

    private void showRules(View view) {
        event_info.setVisibility(View.INVISIBLE);
        findViewById(R.id.rules_layout).setVisibility(View.VISIBLE);
    }

    private void call(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},1);
                startActivity(callIntent);
            }
        }
        startActivity(callIntent);
    }

    private void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",emailId, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding " + event_name + " Event");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    private void getData(String event_name) {
        FirebaseFirestore.getInstance().collection("Events")
                .document(event_name)
                .get().addOnCompleteListener(task -> {
                    if(task.getResult().exists()) {
                        Map<String, Object> data = task.getResult().getData();
                        assert data != null;

                        person.setText(Objects.requireNonNull(data.get("contact")).toString());
                        Picasso.get().load(Objects.requireNonNull(data.get("image")).toString()).into(image);
                        phone = Objects.requireNonNull(data.get("phone")).toString();
                        emailId = Objects.requireNonNull(data.get("email")).toString();
                        //rules.setText(RulesRCVCreator.format(Objects.requireNonNull(data.get("rules")).toString()));
                        new RulesRCVCreator(rules, Objects.requireNonNull(data.get("rules")).toString()).create();
                        desc.setText(Objects.requireNonNull(data.get("desc")).toString());
                        date.setText(Objects.requireNonNull(data.get("date")).toString());
                        time.setText(Objects.requireNonNull(data.get("time")).toString());
                        venue.setText(Objects.requireNonNull(data.get("venue")).toString());
                        prize.setText("Prize : ₹" + Objects.requireNonNull(data.get("prize")).toString());
                        event_info.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
}