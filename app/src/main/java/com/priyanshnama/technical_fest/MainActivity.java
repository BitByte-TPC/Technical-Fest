package com.priyanshnama.technical_fest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static final int GOOGLE_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) open_home();
        else signIn();
    }

    private void signIn() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new_task -> {
                            if (new_task.isSuccessful()) {
                                checkData(Objects.requireNonNull(mAuth.getCurrentUser()));
                            } else Toast.makeText(this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        });
            } catch (ApiException ignored) {}
        }
    }

    private void checkData(FirebaseUser account) {
        boolean result = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(Objects.requireNonNull(account.getUid()))
                .get()
                .isSuccessful();

        if(!result) createData(account);
        open_home();
    }

    private void createData(FirebaseUser account) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", account.getEmail());
        data.put("name", account.getDisplayName());
        data.put("festId",getfestId(account));
        data.put("pass",0);
        FirebaseFirestore.getInstance().collection("users")
                .document(account.getUid())
                .set(data)
                .addOnSuccessListener(documentReference -> Toast.makeText(this,"Account Created",Toast.LENGTH_LONG).show());
    }

    private String getfestId(FirebaseUser account) {
        String name = account.getDisplayName();
        assert name != null;
        StringBuilder festId = new StringBuilder("" + name.charAt(0) + name.charAt(name.indexOf(" ") + 1));
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String cur = sdf.format(new Date());
        String date = "" + cur.charAt(0) + cur.charAt(1) + cur.charAt(3) + cur.charAt(4) + cur.charAt(17) + cur.charAt(18) + cur.charAt(20) + cur.charAt(21) + cur.charAt(23) + cur.charAt(24);
        festId.append(date);
        return festId.toString();
    }

    private void open_home() {
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
    }
}