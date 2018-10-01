package com.example.courtin.myapplication.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.courtin.myapplication.R;
import com.example.courtin.myapplication.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<ViewHolderChat> {

    private List<Message> messageList;

    public MyAdapter() {
        messageList = new ArrayList<>();
    }

    public void sendMessage(Message m){
        messageList.add(m);
    }

    @NonNull
    @Override
    public ViewHolderChat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, viewGroup, false);
        return new ViewHolderChat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChat viewHolderChat, int i) {
        viewHolderChat.bindData(messageList.get(i));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
