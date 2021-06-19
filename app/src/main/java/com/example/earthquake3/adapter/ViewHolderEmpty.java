package com.example.earthquake3.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquake3.R;

//this Empty ViewHolder
class ViewHolderEmpty extends RecyclerView.ViewHolder {

    Button button;
    TextView textView;



    public ViewHolderEmpty(@NonNull View itemView) {
        super(itemView);
        button = itemView.findViewById(R.id.retryBtnEmpty);
        textView = itemView.findViewById(R.id.message_toUser_Empty);



    }

}