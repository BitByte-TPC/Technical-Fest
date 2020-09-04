package com.priyanshnama.technical_fest;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavController bottomNavController = Navigation.findNavController(this, R.id.bottom_nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, bottomNavController);

        Log.d("TAG", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser()==null) finish();
    }

    /*
    discarded code snippet due to material theme

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
            .build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    */

@Override public void onBackPressed() {
    Fragment fragment = getFragmentManager().findFragmentById(R.id.navigation_events);
    if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
        super.onBackPressed();
    }
    }
}