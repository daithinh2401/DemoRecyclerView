package com.example.demorecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private ArrayList<String> mListKey;
    private Context mContext;
    private GradientDrawable mGradientDrawable;

    public RecyclerViewAdapter(Context context, ArrayList<String> keys) {
        mContext = context;
        mListKey = keys;

        mGradientDrawable = (GradientDrawable) mContext.getDrawable(R.drawable.shape_for_item);
    }

    public void setData(ArrayList<String> data){
        mListKey = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        // Set random color to item layout
        // This is optional
        int randomColor = getRandomColor();
        mGradientDrawable.setColor(randomColor);


        // Using algorithm to make 2 lines if text has more than 1 line.
        String key = mListKey.get(position);
        key = AppUtils.replaceString(key);

        holder.key.setText(key);
    }

    private int getRandomColor(){
        Random rand = new Random();
        int red = rand.nextInt(200);
        int green = rand.nextInt(200);
        int blue = rand.nextInt(200);

        return Color.rgb(red,green,blue);
    }

    @Override
    public int getItemCount() {
        return mListKey.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        TextView key;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.item_layout);
            key = itemView.findViewById(R.id.key);
        }
    }

}
