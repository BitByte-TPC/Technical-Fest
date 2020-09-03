package com.priyanshnama.technical_fest.ui.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Events");
    }

    public LiveData<String> getText() {
        return mText;
    }
}