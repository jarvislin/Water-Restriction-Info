package com.jarvislin.waterrestrictioninfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.filippudak.ProgressPieView.ProgressPieView;
import com.jarvislin.waterrestrictioninfo.model.Reservoir;

import java.util.ArrayList;

/**
 * Created by Jarvis Lin on 2015/3/24.
 */
public class ReservoirAdapter extends RecyclerView.Adapter<ReservoirAdapter.ViewHolder> {

    private ArrayList<Reservoir> mList;
    private Context mContext;

    public ReservoirAdapter(Context context, ArrayList<Reservoir> list){
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_reservoir, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.time.setText(mList.get(position).getTime());
        holder.capacity.setProgress((int)mList.get(position).getCapacity());
        holder.capacity.setText(String.valueOf(mList.get(position).getCapacity()) + "%");
        holder.capacity.setTextSize(20);

        if(mList.get(position).getDifferentialLevel().equals("--")) {
            holder.differentialLevel.setText("無資料");
            holder.differentialLevel.setTextColor(mContext.getResources().getColor(R.color.secondary_text));
        } else if(Float.valueOf(mList.get(position).getDifferentialLevel()) >= 0) {
            holder.differentialLevel.setText("昨日 +" + mList.get(position).getDifferentialLevel() + " 公尺");
            holder.differentialLevel.setTextColor(mContext.getResources().getColor(R.color.blue500));
        } else {
            holder.differentialLevel.setText("昨日 " + mList.get(position).getDifferentialLevel() + " 公尺");
            holder.differentialLevel.setTextColor(mContext.getResources().getColor(R.color.red500));
        }



        //set color
        //50 ~ 100 = blue
        //30 ~ 49 = orange
        //0 ~ 29 = red

        if(mList.get(position).getCapacity() >= 50) {
            // blue
            holder.capacity.setProgressColor(mContext.getResources().getColor(R.color.blue300));
            holder.capacity.setStrokeColor(mContext.getResources().getColor(R.color.blue500));
            holder.capacity.setTextColor(mContext.getResources().getColor(R.color.blue900));
        } else if(mList.get(position).getCapacity() < 50 && mList.get(position).getCapacity() >= 30) {
            //orange
            holder.capacity.setProgressColor(mContext.getResources().getColor(R.color.orange300));
            holder.capacity.setStrokeColor(mContext.getResources().getColor(R.color.orange500));
            holder.capacity.setTextColor(mContext.getResources().getColor(R.color.orange900));
        } else {
            //red
            holder.capacity.setProgressColor(mContext.getResources().getColor(R.color.red300));
            holder.capacity.setStrokeColor(mContext.getResources().getColor(R.color.red500));
            holder.capacity.setTextColor(mContext.getResources().getColor(R.color.red900));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView time;
        public TextView differentialLevel;
        public ProgressPieView capacity;

        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.reservoir_name);
            time = (TextView)v.findViewById(R.id.reservoir_time);
            differentialLevel = (TextView)v.findViewById(R.id.reservoir_differential_level);
            capacity = (ProgressPieView)v.findViewById(R.id.reservoir_capacity);
        }
    }
}
