package com.priyanshnama.technical_fest.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.priyanshnama.technical_fest.IOnBackPressed;
import com.priyanshnama.technical_fest.R;

public class EventsFragment extends Fragment implements IOnBackPressed {
    private EventsViewModel eventsViewModel;
    private Boolean isClubSelected;
    private TextView title, clubName;
    private ProgressBar progressCircular;
    private ImageView back;
    private RecyclerView recyclerView;
    private View events, club;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel.class);
        FirebaseApp.initializeApp(requireContext());
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        title = root.findViewById(R.id.title);
        events = root.findViewById(R.id.events);
        clubName = root.findViewById(R.id.club_name);
        club = root.findViewById(R.id.club);
        back = root.findViewById(R.id.back);
        progressCircular = root.findViewById(R.id.progressCircular);

        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setNestedScrollingEnabled(false);

        initializeClubCards(root);
        loadState();
        back.setOnClickListener(this::revertToEvents);

        eventsViewModel.getText().observe(getViewLifecycleOwner(), title::setText);
        return root;
    }



    private void initializeClubCards(View view) {
        view.findViewById(R.id.racing).setOnClickListener(this::expand);
        view.findViewById(R.id.aps).setOnClickListener(this::expand);
        view.findViewById(R.id.coding).setOnClickListener(this::expand);
        view.findViewById(R.id.cad).setOnClickListener(this::expand);
        view.findViewById(R.id.afc).setOnClickListener(this::expand);
        view.findViewById(R.id.electronics).setOnClickListener(this::expand);
        view.findViewById(R.id.photo).setOnClickListener(this::expand);
        view.findViewById(R.id.bmc).setOnClickListener(this::expand);
        view.findViewById(R.id.robotics).setOnClickListener(this::expand);
    }


    private void loadState() {
        isClubSelected = eventsViewModel.isClubSelected();
        if(isClubSelected){
            events.setVisibility(View.INVISIBLE);
            club.setVisibility(View.VISIBLE);
            clubName.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }
    }

    private void expand(View v) {
        isClubSelected = true;
        eventsViewModel.setClubSelected(true);
        String txt_clubName = (String) ((TextView) v).getText();
        clubName.setText(txt_clubName);
        events.setVisibility(View.INVISIBLE);
        club.setVisibility(View.VISIBLE);
        clubName.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        progressCircular.setVisibility(View.VISIBLE);
        eventsViewModel.listEvents(requireContext(), clubName.getText().toString(), recyclerView);
        progressCircular.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onBackPressed() {
        if (isClubSelected) {
            revertToEvents(null);
            return true;
        } else {
            return false;
        }
    }

    private void revertToEvents(View view) {
        isClubSelected = false;
        events.setVisibility(View.VISIBLE);
        club.setVisibility(View.INVISIBLE);
        clubName.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        eventsViewModel.getText().observe(getViewLifecycleOwner(), title::setText);
    }
}