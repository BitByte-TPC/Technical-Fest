package com.priyanshnama.technical_fest.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AttractionsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AttractionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is attractions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}