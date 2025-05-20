package com.example.androidproyectounivalle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproyectounivalle.R;
import com.example.androidproyectounivalle.models.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonList;
    private Context context;
    private OnPokemonClickListener listener;

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon, int position);
    }

    public PokemonAdapter(List<Pokemon> pokemonList, Context context, OnPokemonClickListener listener) {
        this.pokemonList = pokemonList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);

        holder.tvName.setText(pokemon.getName());
        holder.tvType.setText(pokemon.getType());
        holder.tvId.setText("#" + pokemon.getId());

        // Cargar imagen con Glide
        Glide.with(context)
                .load(pokemon.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivPokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void updatePokemonList(List<Pokemon> newPokemonList) {
        this.pokemonList = newPokemonList;
        notifyDataSetChanged();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvType, tvId;
        ImageView ivPokemon;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvPokemonName);
            tvType = itemView.findViewById(R.id.tvPokemonType);
            tvId = itemView.findViewById(R.id.tvPokemonId);
            ivPokemon = itemView.findViewById(R.id.ivPokemon);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onPokemonClick(pokemonList.get(position), position);
                }
            });
        }
    }
}
