package com.priyanshnama.technical_fest.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.priyanshnama.technical_fest.R;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        final TextView textView = root.findViewById(R.id.title);
        eventsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}