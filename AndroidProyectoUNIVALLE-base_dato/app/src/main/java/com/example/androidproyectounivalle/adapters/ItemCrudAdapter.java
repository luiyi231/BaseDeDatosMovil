package com.example.androidproyectounivalle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.models.Item;

import java.util.List;

public class ItemCrudAdapter extends RecyclerView.Adapter<ItemCrudAdapter.ItemViewHolder> {

    private List<Item> items;
    private Context context;
    private OnItemCrudListener listener;

    public interface OnItemCrudListener {
        void onEditClick(Item item, int position);
        void onDeleteClick(Item item, int position);
        void onItemClick(Item item, int position);
    }

    public ItemCrudAdapter(List<Item> items, Context context, OnItemCrudListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crud, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);

        holder.tvItemTitle.setText(item.getTitle());
        holder.tvItemDescription.setText(item.getDescription());

        // Configurar el indicador de estado según si está activo o no
        holder.statusIndicator.setBackgroundResource(
                item.isActive() ? R.drawable.circle_green : R.drawable.circle_red);

        // Configurar listeners
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(item, holder.getAdapterPosition());
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(item, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Item> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void updateItem(Item item, int position) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemTitle;
        TextView tvItemDescription;
        View statusIndicator;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tvItemTitle);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
