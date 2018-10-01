package com.example.courtin.myapplication.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.courtin.myapplication.R;
import com.example.courtin.myapplication.models.Message;

public class ViewHolderChat extends RecyclerView.ViewHolder {

    private TextView pseudo;
    private TextView message;

    public ViewHolderChat(@NonNull View itemView) {
        super(itemView);

        pseudo = itemView.findViewById(R.id.pseudo);
        message = itemView.findViewById(R.id.text);

    }

    public void bindData(Message message){
        this.pseudo.setText(message.pseudo);
        this.message.setText(message.message);
    }
}
