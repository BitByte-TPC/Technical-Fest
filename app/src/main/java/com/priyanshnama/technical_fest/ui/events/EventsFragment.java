package com.priyanshnama.technical_fest.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.priyanshnama.technical_fest.IOnBackPressed;
import com.priyanshnama.technical_fest.R;

public class EventsFragment extends Fragment implements IOnBackPressed {
    private EventsViewModel eventsViewModel;
    private Boolean isClubSelected;
    private TextView title;
    private TextView clubName;
    private View events, club;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        title = root.findViewById(R.id.title);
        events = root.findViewById(R.id.events);
        clubName = root.findViewById(R.id.club_name);
        club = root.findViewById(R.id.club);

        root.findViewById(R.id.racing).setOnClickListener(this::expand);
        root.findViewById(R.id.aps).setOnClickListener(this::expand);
        root.findViewById(R.id.coding).setOnClickListener(this::expand);
        root.findViewById(R.id.cad).setOnClickListener(this::expand);
        root.findViewById(R.id.afc).setOnClickListener(this::expand);
        root.findViewById(R.id.electronics).setOnClickListener(this::expand);
        root.findViewById(R.id.photo).setOnClickListener(this::expand);
        root.findViewById(R.id.bmc).setOnClickListener(this::expand);
        root.findViewById(R.id.robotics).setOnClickListener(this::expand);

        loadState();

        eventsViewModel.getText().observe(getViewLifecycleOwner(), title::setText);
        return root;
    }

    private void loadState() {
        isClubSelected = eventsViewModel.isClubSelected();
        if(isClubSelected){
            events.setVisibility(View.INVISIBLE);
            club.setVisibility(View.VISIBLE);
        }
    }

    private void expand(View v) {
        isClubSelected = true;
        eventsViewModel.setClubSelected(true);
        String txt_clubName = (String) ((TextView) v).getText();
        clubName.setText(txt_clubName);
        events.setVisibility(View.INVISIBLE);
        club.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onBackPressed() {
        if (isClubSelected) {
            revertToEvents();
            return true;
        } else {
            return false;
        }
    }

    private void revertToEvents() {
        isClubSelected = false;
        events.setVisibility(View.VISIBLE);
        club.setVisibility(View.INVISIBLE);
        eventsViewModel.getText().observe(getViewLifecycleOwner(), title::setText);
    }
}