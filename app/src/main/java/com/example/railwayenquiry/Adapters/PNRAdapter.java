package com.example.railwayenquiry.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.railwayenquiry.R;

import java.util.List;

public class PNRAdapter extends RecyclerView.Adapter<PNRAdapter.MyViewHolder> {
    
    public List<PNRItem> PNRList;
    
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView status,index;

        public MyViewHolder(View view) {
            super(view);
            status = (TextView) view.findViewById(R.id.status);
            index = (TextView) view.findViewById(R.id.index);

        }
    }

    public PNRAdapter(List<PNRItem> PNRList){ this.PNRList=PNRList;}

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pnr_item, parent, false);
            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            PNRItem PNR = PNRList.get(position);
            holder.status.setText(PNR.getStatus());
            String index="Passenger "+Integer.toString(position+1);
            holder.index.setText(index);
        }

        @Override
        public int getItemCount() {
            if(PNRList!=null)
                return PNRList.size();
            else
                return 0;
        }


        public void setRows(List<PNRItem> pnrlist, boolean live){
            PNRList = pnrlist;
            notifyDataSetChanged();
        }

    }
