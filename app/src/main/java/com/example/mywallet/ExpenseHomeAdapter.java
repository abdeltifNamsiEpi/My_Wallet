package com.example.mywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseHomeAdapter extends RecyclerView.Adapter<ExpenseHomeAdapter.ExpenseHomeViewHolder> {
    private Context mContext;
    private List<Expense> mData;

    public ExpenseHomeAdapter(Context mContext, List<Expense> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public ExpenseHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(mContext).inflate(R.layout.home_recyclerview_item_expense,parent,false);
        return  new ExpenseHomeAdapter.ExpenseHomeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHomeViewHolder holder, int position) {
        holder.homeExpenseItemCategory.setText(mData.get(position).getExpenseCategory());
        holder.homeExpenseItemAmount.setText(mData.get(position).getExpenseMoney());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ExpenseHomeViewHolder extends RecyclerView.ViewHolder {
        TextView homeExpenseItemCategory,homeExpenseItemAmount;
        public ExpenseHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            homeExpenseItemAmount=itemView.findViewById(R.id.home_expense_item_amount);
            homeExpenseItemCategory=itemView.findViewById(R.id.home_expense_item_category);
        }
    }
}
