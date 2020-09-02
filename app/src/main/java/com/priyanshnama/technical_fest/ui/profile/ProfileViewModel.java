package com.priyanshnama.technical_fest.ui.profile;

import android.content.SharedPreferences;
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
    private SharedPreferences userInfo;

    public ProfileViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = new MutableLiveData<>();
        email = new MutableLiveData<>();
        festId = new MutableLiveData<>();
        pass = new MutableLiveData<>();
        photoUrl = new MutableLiveData<>();
        try {
            setLocalData();
        }catch (Exception ignored){}
        getData();
        try {
            updateLocal();
        }catch (Exception ignored){}
    }

    private void setLocalData() {
        SharedPreferences.Editor editor = userInfo.edit();
        editor.apply();

        name.setValue(userInfo.getString("name","Name"));
        email.setValue(userInfo.getString("email","Email"));
        pass.setValue(userInfo.getInt("pass",0));
        festId.setValue(userInfo.getString("festId","festId"));
        photoUrl.setValue(Uri.parse(userInfo.getString("photoUrl","null")));
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

    private void updateLocal() {
        SharedPreferences.Editor editor = userInfo.edit();
        editor.apply();

        editor.putString("name", user.getDisplayName());
        editor.putString("email",user.getEmail());
        editor.putString("uid",user.getUid());
        editor.putString("festId",festId.getValue());
        editor.putInt("pass",pass.getValue());
        editor.commit();
    }

    public MutableLiveData<String> getName(){return name;}

    public MutableLiveData<String> getEmail() {return email;}

    public MutableLiveData<String> getFestId() {return festId;}

    public MutableLiveData<Integer> getPass() {return pass;}

    public MutableLiveData<Uri> getPhotoUrl() {return photoUrl;}

    public void sendPrefrence(SharedPreferences userInfo) {
        this.userInfo = userInfo;
    }
}