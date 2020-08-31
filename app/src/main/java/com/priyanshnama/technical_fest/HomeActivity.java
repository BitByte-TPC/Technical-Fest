package com.priyanshnama.technical_fest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView sideNavigationView = findViewById(R.id.side_nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.contact)
                .setDrawerLayout(drawer)
                .build();
        NavController sideNavController = Navigation.findNavController(this, R.id.side_nav_host_fragment);
        NavigationUI.setupWithNavController(sideNavigationView, sideNavController);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavController bottomNavController = Navigation.findNavController(this, R.id.bottom_nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, bottomNavController);

        findViewById(R.id.navigate).setOnClickListener(this::navigation);
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


    private void navigation(View view) {
        NavController sideNavController = Navigation.findNavController(this, R.id.side_nav_host_fragment);
        if (!NavigationUI.navigateUp(sideNavController, mAppBarConfiguration)) {
            super.onSupportNavigateUp();
        }
    }

    public static class NullFragment extends Fragment {

        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return null;
        }
    }
}