package com.priyanshnama.technical_fest.ui.profile;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

public class ProfileViewModel extends ViewModel {
    private FirebaseUser user;
    private MutableLiveData<String> name, email, festId;
    private MutableLiveData<Integer> pass;
    private MutableLiveData<Uri> photoUrl;

    public ProfileViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = new MutableLiveData<>();
        email = new MutableLiveData<>();
        festId = new MutableLiveData<>();
        pass = new MutableLiveData<>();
        photoUrl = new MutableLiveData<>();
        getData();
    }

    public void getData() {
        name.setValue(user.getDisplayName());
        email.setValue(user.getEmail());
        photoUrl.setValue(user.getPhotoUrl());

        FirebaseFirestore.getInstance().collection("users")
                .document(Objects.requireNonNull(user.getUid()))
                .get().addOnCompleteListener(task -> {
                    Map<String, Object> data = task.getResult().getData();
                    assert data != null;
                    festId.setValue(Objects.requireNonNull(data.get("festId")).toString());
                    pass.setValue(Integer.parseInt(Objects.requireNonNull(data.get("pass")).toString()));
                });
    }

    public MutableLiveData<String> getName(){return name;}

    public MutableLiveData<String> getEmail() {return email;}

    public MutableLiveData<String> getFestId() {return festId;}

    public MutableLiveData<Integer> getPass() {return pass;}

    public MutableLiveData<Uri> getPhotoUrl() {return photoUrl;}
}