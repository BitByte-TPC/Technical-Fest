package com.priyanshnama.technical_fest.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.priyanshnama.technical_fest.MainActivity;
import com.priyanshnama.technical_fest.R;
import com.priyanshnama.technical_fest.UpgradePassActivity;
import com.priyanshnama.technical_fest.ui.options.AboutActivity;
import com.priyanshnama.technical_fest.ui.options.CAPortalActivity;
import com.priyanshnama.technical_fest.ui.options.ContactUsActivity;
import com.priyanshnama.technical_fest.ui.options.TeamActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView name, email, festId, passView;
    private ImageView profilePic;
    private Integer pass;
    private Dialog panel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        festId = root.findViewById(R.id.uid);
        profilePic = root.findViewById(R.id.profile_image);
        passView = root.findViewById(R.id.pass);

        root.findViewById(R.id.upgrade).setOnClickListener(v -> startActivity(new Intent(getContext(), UpgradePassActivity.class)));
        root.findViewById(R.id.options_menu).setOnClickListener(v-> panel.show());

        SharedPreferences userInfo =  this.requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        profileViewModel.sendPrefrence(userInfo);
        setData();
        prepareOptions();
        return root;
    }

    private void prepareOptions() {
        panel = new Dialog(requireContext());
        panel.setContentView(R.layout.options_menu);
        panel.setCancelable(true);
        Objects.requireNonNull(panel.getWindow()).setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        panel.findViewById(R.id.contact).setOnClickListener(v-> startActivity(new Intent(requireContext(), ContactUsActivity.class)));
        panel.findViewById(R.id.about).setOnClickListener(v-> startActivity(new Intent(requireContext(), AboutActivity.class)));
        panel.findViewById(R.id.ca).setOnClickListener(v-> startActivity(new Intent(requireContext(), CAPortalActivity.class)));
        panel.findViewById(R.id.team).setOnClickListener(v-> startActivity(new Intent(requireContext(), TeamActivity.class)));
        panel.findViewById(R.id.signOut).setOnClickListener(this::signOut);
    }

    private void setData() {
        profileViewModel.getName().observe(getViewLifecycleOwner(), s -> name.setText(s));
        profileViewModel.getEmail().observe(getViewLifecycleOwner(), s -> email.setText(s));
        profileViewModel.getFestId().observe(getViewLifecycleOwner(), s -> festId.setText(s));
        profileViewModel.getPass().observe(getViewLifecycleOwner(), integer -> passView.setText(String.valueOf(integer)));
        profileViewModel.getPhotoUrl().observe(getViewLifecycleOwner(),
                uri -> Picasso.get().load(uri.toString()).into(profilePic));
    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignIn.getClient(requireContext(), gso).signOut().addOnCompleteListener(task -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            requireActivity().finish();
        });
    }

}