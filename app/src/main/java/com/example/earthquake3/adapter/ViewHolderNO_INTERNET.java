package com.example.earthquake3.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquake3.R;

//this NoInternet ViewHolder
 class ViewHolderNO_INTERNET extends RecyclerView.ViewHolder {
    Button button;
    TextView textView;
    OnButtonClickListener clickListener;

    public ViewHolderNO_INTERNET(View itemView,OnButtonClickListener mClick) {
        super(itemView);
        button = itemView.findViewById(R.id.retryBtn_noInternetConnection);
        textView = itemView.findViewById(R.id.message_toUser_NO_Internet);
        clickListener=mClick;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.OnButtonClick();
            }
        });
    }

}