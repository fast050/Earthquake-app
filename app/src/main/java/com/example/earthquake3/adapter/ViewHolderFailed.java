package com.example.earthquake3.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquake3.R;

//this Failed ViewHolder
class ViewHolderFailed extends RecyclerView.ViewHolder {

    Button button;
    TextView textView;
    OnButtonClickListener clickListener;

    public ViewHolderFailed(@NonNull View itemView,OnButtonClickListener mClick) {
        super(itemView);
        button = itemView.findViewById(R.id.retryBtnError);
        textView = itemView.findViewById(R.id.message_toUser_Failed);
        clickListener=mClick;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClick.OnButtonClick();
            }
        });

    }
}