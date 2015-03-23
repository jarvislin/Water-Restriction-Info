package com.jarvislin.waterrestrictioninfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView time;
        public ProgressPieView capacity;

        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.reservoir_name);
            time = (TextView)v.findViewById(R.id.reservoir_time);
            capacity = (ProgressPieView)v.findViewById(R.id.reservoir_capacity);
        }
    }
}
