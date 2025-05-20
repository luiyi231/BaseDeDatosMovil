package com.example.androidproyectounivalle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.models.Item;

import java.util.List;

public class AvanzadoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ACTIVE = 1;
    private static final int VIEW_TYPE_INACTIVE = 2;

    private List<Item> items;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onItemClick(Item item, int position);
        void onActionButtonClick(Item item, int position);
    }

    public AvanzadoAdapter(List<Item> items, OnItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Item item = items.get(position);
        return item.isActive() ? VIEW_TYPE_ACTIVE : VIEW_TYPE_INACTIVE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ACTIVE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_avanzado_activo, parent, false);
            return new ActiveViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_avanzado_inactivo, parent, false);
            return new InactiveViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        if (holder instanceof ActiveViewHolder) {
            bindActiveViewHolder((ActiveViewHolder) holder, item, position);
        } else if (holder instanceof InactiveViewHolder) {
            bindInactiveViewHolder((InactiveViewHolder) holder, item, position);
        }
    }

    private void bindActiveViewHolder(ActiveViewHolder holder, Item item, int position) {
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
        holder.ivImage.setImageResource(item.getImageResource());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item, position);
            }
        });

        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActionButtonClick(item, position);
            }
        });
    }

    private void bindInactiveViewHolder(InactiveViewHolder holder, Item item, int position) {
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
        holder.ivImage.setImageResource(item.getImageResource());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item, position);
            }
        });

        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActionButtonClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ActiveViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivImage;
        ImageButton btnAction;

        public ActiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }

    public static class InactiveViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivImage;
        ImageButton btnAction;

        public InactiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}
