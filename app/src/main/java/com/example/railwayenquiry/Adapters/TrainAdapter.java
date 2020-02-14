package com.example.railwayenquiry.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.railwayenquiry.R;

import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.MyViewHolder> {

    private List<TrainItem> TrainList;
    private List<RescheduleItem> TrainList2;
    private List<CancelItem> TrainList3;
    String contexts[]={"StationScheduleFragment2.class", "RPageFragment.class","CPageFragment.class"};
    String contextString;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView train_name, train_no, arrival_time,departure_time;



        public MyViewHolder(View view) {
            super(view);
            train_name = (TextView) view.findViewById(R.id.trainname);
            arrival_time = (TextView) view.findViewById(R.id.arrivaltime);
            departure_time = (TextView) view.findViewById(R.id.departuretime);

        }
    }


    public TrainAdapter(List<TrainItem> TrainList, List<RescheduleItem> TrainList2,List<CancelItem> TrainList3,String contextString) {
        this.contextString=contextString;
        if(contextString.equals(contexts[0]))
            this.TrainList = TrainList;
        else if(contextString.equals(contexts[1]))
            this.TrainList2 = TrainList2;
        else if(contextString.equals(contexts[2]))
            this.TrainList3 = TrainList3;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.train_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.d("StationAdapter:", "Holder");
        if(contextString.equals(contexts[0]))
        {
            Log.d("TrainAdapter:", "Holder");
            TrainItem TT = TrainList.get(position);
            holder.train_name.setText(TT.getTrain_no() + " - " + TT.getTrain_name());
            holder.arrival_time.setText("STA\n" + TT.getArrival_time());
            holder.departure_time.setText("ETA\n" + TT.getDeparture_time());
        }
        else if(contextString.equals(contexts[1]))
        {
            RescheduleItem TT = TrainList2.get(position);
            holder.train_name.setText(TT.getTrain_no() + " - " + TT.getTrain_name());
            holder.arrival_time.setText("New Time:\n" + TT.getArrival_time());
            holder.departure_time.setText(TT.getArrival_date());

        }
        else if(contextString.equals(contexts[2]))
        {
            CancelItem TT = TrainList3.get(position);
            holder.train_name.setText(TT.getTrain_no() + " - " + TT.getTrain_name());
            holder.arrival_time.setText("Time:\n" + TT.getArrival_time());
            holder.departure_time.setText("");

        }
    }

    @Override
    public int getItemCount() {
        if(TrainList!=null)
            return TrainList.size();

        else if(TrainList2!=null)
            return TrainList2.size();

        else if(TrainList3!=null)
            return TrainList3.size();

        else
            return 0;
    }


    public void setRows(List<TrainItem> TrainList, List<RescheduleItem> TrainList2,List<CancelItem> TrainList3){



        if(contextString.equals(contexts[0])){
            this.TrainList = TrainList;
            Log.d("StationAdapter:", "notfifydatasetchanged");}
        else if(contextString.equals(contexts[1])){
            this.TrainList2 = TrainList2;
            Log.d("RescheduleAdapter:", "notfifydatasetchanged");}
        else if(contextString.equals(contexts[2])){
            this.TrainList3 = TrainList3;
            Log.d("CancelAdapter:", "notfifydatasetchanged");}

        notifyDataSetChanged();

    }
}
