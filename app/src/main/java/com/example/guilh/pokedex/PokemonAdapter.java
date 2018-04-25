package com.example.guilh.pokedex;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    private int count;
    private int stepNumber;
    private int startCount;
    private List<Pokemon> pokemon;

    public PokemonAdapter(Activity context, List<Pokemon> pokemon, int startCount, int stepNumber) {
        super(context,0, pokemon);
        this.pokemon = pokemon;
        this.startCount = Math.min(startCount, pokemon.size());
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    @Override
    public int getCount() {
        return count;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.group_item, parent, false);
        }

        Pokemon currentPokemon = getItem(position);

        TextView statTextView = (TextView) listItemView.findViewById(R.id.stat_text_view);
        statTextView.setVisibility(View.INVISIBLE);

        TextView valueTextView = (TextView) listItemView.findViewById(R.id.value_text_view);
        String pokemonName = currentPokemon.getNumber() + ". " + currentPokemon.getName();
        valueTextView.setText(pokemonName);

        return listItemView;
    }

    public boolean showMore() {
        if (count == pokemon.size()){
            return true;
        } else {
            count = Math.min(count + stepNumber, pokemon.size());
            notifyDataSetChanged();
            return endReached();
        }
    }

    public boolean endReached() {
        return count == pokemon.size();
    }

    public void reset() {
        count = startCount;
        notifyDataSetChanged();
    }
}
