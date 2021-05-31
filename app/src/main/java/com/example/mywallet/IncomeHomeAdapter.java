package com.example.mywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IncomeHomeAdapter extends RecyclerView.Adapter<IncomeHomeAdapter.IncomeHomeViewHolder>{
    private Context mContext;
    private List<Income> mData;

    public IncomeHomeAdapter(Context mContext, List<Income> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public IncomeHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(mContext).inflate(R.layout.home_recyclerview_item,parent,false);
        return  new IncomeHomeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeHomeViewHolder holder, int position) {
        holder.homeItemCategory.setText(mData.get(position).getIncomeCategory());
        holder.homeItemAmount.setText(mData.get(position).getIncomeMoney());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class IncomeHomeViewHolder extends RecyclerView.ViewHolder{
        TextView homeItemCategory,homeItemAmount;
        public IncomeHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            homeItemAmount=itemView.findViewById(R.id.home_item_amount);
            homeItemCategory=itemView.findViewById(R.id.home_item_category);
        }
    }
}
