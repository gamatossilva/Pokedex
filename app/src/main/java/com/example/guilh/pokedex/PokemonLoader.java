package com.example.guilh.pokedex;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PokemonLoader extends AsyncTaskLoader<List<Pokemon>> {
    private int offset;
    List<Pokemon> mData;

    public PokemonLoader(Context context, int offset) {
        super(context);
        this.offset = offset;
    }

    @Override
    protected void onStartLoading() {

        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Pokemon> loadInBackground() {

        final List<Pokemon> pokemons = new ArrayList<>();

        PokeApiInterface apiService = ApiClient.getClient().create(PokeApiInterface.class);
        Call<PokeList> call = apiService.getPokeList(20, offset);
        Response<PokeList> response = null;

        try {
            response = call.execute();
            PokeList pokeList;

            if (response.isSuccessful()) {
                pokeList = response.body();

                for (int i = 0; i < 20; i++) {

                    pokemons.add(new Pokemon(pokeList.getResults().get(i).getName(), pokeList.getResults().get(i).getUrl()));
                }
            } else {
                Log.e("POKEMON", " onResponse: " + response.errorBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    @Override
    public void deliverResult(List<Pokemon> data) {
        mData = data;
        super.deliverResult(data);
    }
}
