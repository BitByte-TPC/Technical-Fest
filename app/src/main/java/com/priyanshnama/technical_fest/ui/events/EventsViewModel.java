package com.priyanshnama.technical_fest.ui.events;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Boolean isClubSelected = false;
    private ArrayList<Event> list = new ArrayList<>();

    public EventsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Event");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public Boolean isClubSelected() {
        return isClubSelected;
    }

    public void setClubSelected(boolean b) {
        this.isClubSelected = b;
    }

    public void listEvents(Context context, String title, RecyclerView recyclerView, ProgressBar progressCircular) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            firebaseDatabase.setLogLevel(Logger.Level.DEBUG);
        }

        Query ref = firebaseDatabase.getReference().child("Competitions").child(title + " Club").endAt(100L);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot event_names : dataSnapshot.getChildren())
                {
                    Event event = event_names.getValue(Event.class);
                    assert event != null;
                    list.add(event);
                }
                recyclerView.setAdapter(new EventAdapter(context,list));
                recyclerView.setVisibility(View.VISIBLE);
                progressCircular.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FB", "Error: " + error);
            }
        });
    }
}