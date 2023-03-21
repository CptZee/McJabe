package com.github.cptzee.mcjabe.Data.Database.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.mcjabe.Data.Food;
import com.github.cptzee.mcjabe.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<Food> list;
    public FoodAdapter(List<Food> list) {
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_card_view, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Food food = list.get(position);
        viewHolder.getName().setText(food.getName());
        viewHolder.getDesc().setText(food.getDescription());
        viewHolder.getPrice().setText("Price: " + food.getPrice() + " PHP");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, desc, price;
        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_name);
            desc = view.findViewById(R.id.item_description);
            price = view.findViewById(R.id.item_price);
        }

        public TextView getName() {
            return name;
        }

        public TextView getDesc() {
            return desc;
        }

        public TextView getPrice() {
            return price;
        }
    }
}
