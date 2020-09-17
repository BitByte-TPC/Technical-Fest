package com.priyanshnama.technical_fest.ui.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.priyanshnama.technical_fest.R;

import java.util.ArrayList;

public class RulesRCVCreator {

    private ArrayList<String> rulesList;
    private RecyclerView RCV;
    private String data;

    public RulesRCVCreator(RecyclerView RCV, String data) {
        this.rulesList = new ArrayList<>();
        this.data = data;
        this.RCV = RCV;
    }

    public void create(){
        this.rulesList = format(data);
        RCVAdapter adapter = new RCVAdapter(this.rulesList);
        this.RCV.setAdapter(adapter);
    }

    public ArrayList<String> format(@NonNull String data) {
        ArrayList<String> rulesList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        for(int i=0;i<data.length();i++){
            if( i!=data.length()-1 && data.charAt(i)=='\\' && data.charAt((i+1))=='n'){
                if(i<=data.length()-4 && data.charAt(i+2)=='\\' && data.charAt((i+3))=='n'){
                    rulesList.add(result.toString());
                    result = new StringBuilder();
                    i+=3;
                } else {
                    result.append('\n');
                    i++;
                }
                continue;
            }
            result.append(data.charAt(i));
        }
        rulesList.add(result.toString());
        return rulesList;
    }
}

class RCVAdapter extends RecyclerView.Adapter {

    private ArrayList<String> rulesList;
    public RCVAdapter(ArrayList<String> rulesList){
        this.rulesList = rulesList;
    }

    public static class VH extends RecyclerView.ViewHolder{

        TextView rule;
        public VH(@NonNull View itemView) {
            super(itemView);
            rule = itemView.findViewById(R.id.rule);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rules, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VH) holder).rule.setText(rulesList.get(position));
    }

    @Override
    public int getItemCount() {
        return rulesList.size();
    }
}
