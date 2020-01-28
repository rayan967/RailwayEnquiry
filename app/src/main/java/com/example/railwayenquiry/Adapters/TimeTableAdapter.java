package com.example.railwayenquiry.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.railwayenquiry.R;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder> {

    private List<TimeTableItem> TTList;
    public boolean live;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView station_name,arrival_time,departure_time;
        public View visited,line;


        public MyViewHolder(View view) {
            super(view);
            station_name = (TextView) view.findViewById(R.id.stationname);
            arrival_time = (TextView) view.findViewById(R.id.arrivaltime);
            departure_time = (TextView) view.findViewById(R.id.departuretime);
            visited = (View) view.findViewById(R.id.view6);
            line=(View) view.findViewById(R.id.view5);

        }
    }


    public TimeTableAdapter(List<TimeTableItem> TTList) {
        this.TTList = TTList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_table_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TimeTableItem TT = TTList.get(position);
        holder.station_name.setText(TT.getTrain_name());
        holder.arrival_time.setText(TT.getArrival_time());
        holder.departure_time.setText(TT.getDeparture_time());
        if(live) {
            if (TT.getVisited()) {
                holder.visited.setBackgroundResource(R.drawable.status_circle_visited);
            } else {
                holder.visited.setBackgroundResource(R.drawable.status_circle);
            }
        }
        else{
            holder.visited.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(TTList!=null)
            return TTList.size();
        else
            return 0;
    }


    public void setRows(List<TimeTableItem> ttlist, boolean live){
        TTList = ttlist;
        this.live=live;
        notifyDataSetChanged();
    }
}
