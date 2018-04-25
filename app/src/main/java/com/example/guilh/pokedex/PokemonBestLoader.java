package com.example.guilh.pokedex;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PokemonBestLoader extends AsyncTaskLoader<List<PokeDetails>> {
    private int amount;
    public PokemonBestLoader(Context context, int amount) {
        super(context);
        this.amount = amount;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<PokeDetails> loadInBackground() {


        final PokeApiInterface apiService = ApiClient.getClient().create(PokeApiInterface.class);
        List<PokeDetails> pokemons = new ArrayList<>();

        for (int i = 0; i <= amount; i++) {
            Call<PokeDetails> call = apiService.getPokeDetail(i);
            Response<PokeDetails> response = null;

            try {
                response = call.execute();
                PokeDetails pokeDetails;

                if (response.isSuccessful()) {
                    pokeDetails = response.body();

                    pokemons.add(pokeDetails);

                } else {
                    Log.e("POKEMON", " onResponse: " + response.errorBody());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("POKEMON", "pomeons: " + pokemons);
        return pokemons;
    }
}
