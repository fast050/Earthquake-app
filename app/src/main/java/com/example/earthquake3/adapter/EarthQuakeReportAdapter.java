package com.example.earthquake3.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquake3.R;
import com.example.earthquake3.model.items.EarthQuakeReportModel;
import com.example.earthquake3.model.items.Feature;
import com.example.earthquake3.util.Constants.LoadingBarState;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthQuakeReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Feature> listEarthQuake;
    private Context mContext;
    private String MessageToUser;
    private LoadingBarState state;
    private final int LOADING = 0, SUCCESS = 1, FAILED = -1, NO_INTERNET = -2, EMPTY = 2;
    private OnButtonClickListener clickListener;


    public EarthQuakeReportAdapter(Context context,OnButtonClickListener clickListener) {
        mContext = context;
        this.clickListener=clickListener;
    }

    public void setState(LoadingBarState state) {
        this.state = state;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {

            case SUCCESS:
                view = LayoutInflater.from(mContext).inflate(R.layout.recyler_earthquake_item, parent, false);
                return new EarthQuakeReportHolderSuccess(view);

            case FAILED:
                view = LayoutInflater.from(mContext).inflate(R.layout.error_layout, parent, false);
                return new ViewHolderFailed(view,clickListener);

            case NO_INTERNET:
                view = LayoutInflater.from(mContext).inflate(R.layout.no_internet_layout, parent, false);
                return new ViewHolderNO_INTERNET(view,clickListener);

            case EMPTY:
                view = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, parent, false);
                return new ViewHolderEmpty(view);

            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.unknow_layout, parent, false);
                return new ViewHolderUnKnow(view);

        }
    }

    public void setListEarthQuake(List<Feature> listEarthQuake) {
        if (listEarthQuake != null) {

            //remove item if mag is null
            for (int i = 0; i < listEarthQuake.size(); i++) {
                if (listEarthQuake.get(i).getProperties().getMag() == null || listEarthQuake.get(i).getProperties().getMag() <= 0)
                    listEarthQuake.remove(listEarthQuake.get(i));
            }

            this.listEarthQuake = listEarthQuake;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof EarthQuakeReportHolderSuccess) {
            Feature item = listEarthQuake.get(position);
            EarthQuakeReportHolderSuccess holderSuccess = (EarthQuakeReportHolderSuccess) holder;

            holderSuccess.mag.setText(FormatDecimal(item.getProperties().getMag()));

            //to set the backgroundColor to the mag
            setShapeBackgroundColor(holderSuccess.mag, item);


            holderSuccess.date1.setText(FormatDate(item.getProperties().getDate()));
            holderSuccess.date2.setText(FormatTime(item.getProperties().getDate()));
            holderSuccess.place1.setText(FormatPlace(item.getProperties().getPlace())[0]);
            holderSuccess.place2.setText(FormatPlace(item.getProperties().getPlace())[1]);

        }
        if (holder instanceof ViewHolderNO_INTERNET)
        {
            ((ViewHolderNO_INTERNET)holder).textView.setText(MessageToUser);
        }

        if (holder instanceof ViewHolderEmpty)
        {
            ((ViewHolderEmpty)holder).textView.setText(MessageToUser);
        }

        if (holder instanceof ViewHolderFailed)
        {
            ((ViewHolderFailed)holder).textView.setText(MessageToUser);
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

        if (state == LoadingBarState.SUCCESS && listEarthQuake != null)
            return listEarthQuake.size();

        if (state == LoadingBarState.FAILED || state == LoadingBarState.EMPTY || state == LoadingBarState.NO_INTERNET)
            return 1;

        return 0;
    }

    public void setMessageToUser(String messageToUser) {
        MessageToUser = messageToUser;
        notifyDataSetChanged();
    }

    //this success ViewHolder
    class EarthQuakeReportHolderSuccess extends RecyclerView.ViewHolder {

        TextView mag, place1, place2, date1, date2;

        public EarthQuakeReportHolderSuccess(@NonNull View itemView) {
            super(itemView);
            mag = itemView.findViewById(R.id.mag);
            place1 = itemView.findViewById(R.id.place1);
            place2 = itemView.findViewById(R.id.place2);
            date1 = itemView.findViewById(R.id.time1);
            date2 = itemView.findViewById(R.id.time2);
        }
    }


    //this to mag color level
    private void setShapeBackgroundColor(TextView mag, Feature itemInfo) {

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getColor(mContext, getMagnitudeColor(itemInfo.getProperties().getMag()));

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

    }


    //get the right color accord to the magnitude from 0 - 10
    private int getMagnitudeColor(Double magnitude) {

        if (magnitude != null) {
            switch (magnitude.intValue()) {
                case 0:
                case 1:
                    return R.color.magnitude1;
                case 2:
                    return R.color.magnitude2;
                case 3:
                    return R.color.magnitude3;
                case 4:
                    return R.color.magnitude4;
                case 5:
                    return R.color.magnitude5;
                case 6:
                    return R.color.magnitude6;
                case 7:
                    return R.color.magnitude7;
                case 8:
                    return R.color.magnitude8;
                default:
                    return R.color.magnitude9;
            }
        } else
            return R.color.m1;
    }

    //get color method convert to color we can use in XmL file
    private int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    //to convert decimal to this 0.0
    private String FormatDecimal(Double decimalToConvert) {
        if (decimalToConvert == null)
            return "Null";

        DecimalFormat formatter = new DecimalFormat("0.0");

        return formatter.format(decimalToConvert);
    }

    //to convert time to readable from milliSeconds
    private static String FormatTime(long timeInMilliSeconds) {
        //to make object form the
        Date dateObject = new Date(timeInMilliSeconds);
        //the time form we need or want
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");


        return dateFormatter.format(dateObject);
    }

    //to convert Date to readable from milliSeconds
    private String FormatDate(long timeInMilliSeconds) {
        //to make object form the
        Date dateObject = new Date(timeInMilliSeconds);
        //the time form we need or want
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");


        return dateFormatter.format(dateObject);
    }


    //to improve the Form of the place
    private String[] FormatPlace(String place) {
        String[] StringArray = new String[2];
        if (place.contains(" of ")) {
            //this need modify
            StringArray[0] = place.substring(0, place.indexOf("of")) + " Of";
            StringArray[1] = place.substring(place.indexOf("of") + 3);
        } else {
            StringArray[0] = "Near the";
            StringArray[1] = place;
        }

        return StringArray;
    }


}