package com.jarvislin.waterrestrictioninfo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jarvislin.waterrestrictioninfo.model.HomepageNews;

import java.util.ArrayList;

/**
 * Created by Jarvis Lin on 2015/3/22.
 */
public class HomepageNewsAdapter extends RecyclerView.Adapter<HomepageNewsAdapter.ViewHolder> {

    private ArrayList<HomepageNews> mList;
    private Context mContext;

    public HomepageNewsAdapter(Context context, ArrayList<HomepageNews> list){
        mList = list;
        mContext = context;
    }

    @Override
    public HomepageNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_homepage_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomepageNewsAdapter.ViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        if(position % 2 == 1){
            holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.light_primary));
        } else {
            holder.background.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public LinearLayout background;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.homepage_title);
            background = (LinearLayout) v.findViewById(R.id.homepage_background);
        }
    }
}
