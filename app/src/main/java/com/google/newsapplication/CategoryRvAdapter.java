package com.google.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRvAdapter extends RecyclerView.Adapter<CategoryRvAdapter.ViewHolder> {

private ArrayList<CategoryRvModel>categoryRvModels;
private Context context;
private CategoryClickInterface categoryClickInterface;

    public CategoryRvAdapter(ArrayList<CategoryRvModel> categoryRvModels, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRvModels = categoryRvModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false);
        return new CategoryRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRvAdapter.ViewHolder holder, int position) {
     CategoryRvModel  categoryRvModel= categoryRvModels.get(position);
     holder.categoryTv.setText(categoryRvModel.getCategory());
        Picasso.get().load(categoryRvModel.getCategoryImageUrl()).into(holder.categoryIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRvModels.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTv;
        private ImageView categoryIv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTv=itemView.findViewById(R.id.categoryText);
            categoryIv=itemView.findViewById(R.id.categoryimg);

        }
    }
}
