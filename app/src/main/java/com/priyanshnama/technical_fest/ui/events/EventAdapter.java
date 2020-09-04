package com.priyanshnama.technical_fest.ui.events;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.priyanshnama.technical_fest.EventInfoActivity;
import com.priyanshnama.technical_fest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventsViewHolder> {

    Context context;
    ArrayList<Event> event;

    public EventAdapter(Context c , ArrayList<Event> list)
    {
        context = c;
        event = list;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventsViewHolder(LayoutInflater.from(context).inflate(R.layout.event_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        String[] event_data = new String[3];
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventInfoActivity.class)
                    .putExtra("event_data",event_data);
            context.startActivity(intent);
        });
        holder.name.setText(event.get(position).getName());
        Picasso.get().load(event.get(position).getImage_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    static class EventsViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView imageView;
        public EventsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
