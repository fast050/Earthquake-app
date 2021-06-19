package com.example.earthquake3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earthquake3.util.Constants.LoadingBarState;
import com.example.earthquake3.R;
import com.example.earthquake3.model.newsmodel.Article;
import com.example.earthquake3.util.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> articleList;
    private final Context mContext;
    private String MessageToUser;
    private OnButtonClickListener clickListener;
    private Constants.LoadingBarState state;
    private final int LOADING = 0, SUCCESS = 1, FAILED = -1, NO_INTERNET = -2, EMPTY = 2;

    public NewsAdapter(Context context, OnButtonClickListener clickListener) {
        mContext = context;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view;

        switch (viewType) {

            case SUCCESS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_article_item, parent, false);
                return new NewsViewHolder(view);

            case FAILED:
                view = LayoutInflater.from(mContext).inflate(R.layout.error_layout, parent, false);
                return new ViewHolderFailed(view, clickListener);

            case NO_INTERNET:
                view = LayoutInflater.from(mContext).inflate(R.layout.no_internet_layout, parent, false);
                return new ViewHolderNO_INTERNET(view, clickListener);

            case EMPTY:
                view = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, parent, false);
                return new ViewHolderEmpty(view);

            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.unknow_layout, parent, false);
                return new ViewHolderUnKnow(view);

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NewsViewHolder) {
            if (articleList != null) {
                Article article = articleList.get(position);
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;

                Glide.with(mContext)
                        .load(article.getUrlToImage())
                        .centerCrop()
                        .placeholder(R.drawable.ic_placeholder_glide)
                        .into(newsViewHolder.imageView);

                newsViewHolder.title.setText(article.getTitle());
                newsViewHolder.uploadTime.setText(FormatTime(article.getPublishedAt()));
            }
        }

        if (holder instanceof ViewHolderNO_INTERNET) {
            ((ViewHolderNO_INTERNET) holder).textView.setText(MessageToUser);
        }

        if (holder instanceof ViewHolderEmpty) {
            ((ViewHolderEmpty) holder).textView.setText(MessageToUser);
        }

        if (holder instanceof ViewHolderFailed) {
            ((ViewHolderFailed) holder).textView.setText(MessageToUser);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (state == LoadingBarState.SUCCESS)
            return SUCCESS;
        if (state == LoadingBarState.FAILED)
            return FAILED;
        if (state == LoadingBarState.EMPTY)
            return EMPTY;
        if (state == LoadingBarState.NO_INTERNET)
            return NO_INTERNET;
        if (state == LoadingBarState.LOADING)
            return LOADING;

        //any number
        return -55;
    }

    @Override
    public int getItemCount() {

        if (state == LoadingBarState.SUCCESS && articleList != null)
            return articleList.size();

        if (state == Constants.LoadingBarState.FAILED || state == LoadingBarState.EMPTY || state == LoadingBarState.NO_INTERNET)
            return 1;

        return 0;
    }


    public void setState(Constants.LoadingBarState loadingBarState) {
        state = loadingBarState;
        notifyDataSetChanged();
    }

    public void setMessageToUser(String s) {
        MessageToUser = s;
        notifyDataSetChanged();
    }

    public void setArticleList(List<Article> articleList) {
        if (articleList != null)
            this.articleList = articleList;
        notifyDataSetChanged();
    }

    private String FormatTime(Date date) {
        DateFormat formatter = new SimpleDateFormat("d/M/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        return formatter.format(date);
    }

    //Success News ViewHolder
    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, uploadTime;
        ImageView imageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.News_title_item);
            uploadTime = itemView.findViewById(R.id.News_uploadtime_item);
            imageView = itemView.findViewById(R.id.News_Image_item);
        }
    }


}
