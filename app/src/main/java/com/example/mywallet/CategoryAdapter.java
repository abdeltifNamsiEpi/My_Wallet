package com.example.mywallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.interfaces.RecyclerViewClickInterface;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Category[] categories;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public CategoryAdapter(Category[] categories,RecyclerViewClickInterface recyclerViewClickInterface) {
        this.categories = categories;
        this.recyclerViewClickInterface=recyclerViewClickInterface;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.bind(categories[position]);

    }

    @Override
    public int getItemCount() {
        return categories.length;
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView categoryIcon;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.name_category);
            categoryIcon=itemView.findViewById(R.id.icon_category_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }


        public void bind(Category category) {
            categoryName.setText(category.name);
            categoryIcon.setImageResource(category.icon);

        }
    }
}










