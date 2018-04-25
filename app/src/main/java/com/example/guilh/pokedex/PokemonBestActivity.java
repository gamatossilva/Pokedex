package com.example.guilh.pokedex;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonBestActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<PokeDetails>> {

    ExpandableListView elvStats;
    PokeTotalAdapter mAdapter;
    private int amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_best);
        elvStats = (ExpandableListView) findViewById(R.id.pokemon_best_stats_list);
        TextView mEmpityStateTextView;

        mAdapter = new PokeTotalAdapter(this, new ArrayList<String>(), new HashMap<String, List<PokeTotal>>());
        elvStats.setAdapter(mAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        mEmpityStateTextView = (TextView) findViewById(R.id.pokemon_best_empity_view);
        elvStats.setEmptyView(mEmpityStateTextView);

        if (networkInfo != null && networkInfo.isConnected()) {
            amount = getIntent().getIntExtra("AMOUNT", 0);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else {
            View loadingIndicator = (ProgressBar) findViewById(R.id.progress_bar_total);
            loadingIndicator.setVisibility(View.GONE);

            mEmpityStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public Loader<List<PokeDetails>> onCreateLoader(int id, Bundle args) {
        return new PokemonBestLoader(this, amount);
    }

    @Override
    public void onLoadFinished(Loader<List<PokeDetails>> loader, List<PokeDetails> pokemons) {
        View loadingIndicator = (ProgressBar) findViewById(R.id.progress_bar_total);
        loadingIndicator.setVisibility(View.GONE);

        if (pokemons != null && !pokemons.isEmpty()) {

            List<String> listGroups = new ArrayList<>();
            List<PokeTotal> pokeTotals = new ArrayList<>();
            List<PokeTotal> pokeNames = new ArrayList<>();
            List<PokeTotal> pokeTotalList = new ArrayList<>();

            Map<String, List<PokeTotal>> listItemsGroup = new HashMap<>();

            Map<String, List<PokeTotal>> statsByPokemon = new HashMap<>();
            Map<String, PokeDetails> detailsByPokemon = new HashMap<>();

            for (int i = 0; i < amount; i++) {

                PokeDetails pokeDetails = pokemons.get(i);
                statsByPokemon.put(pokeDetails.getName(), new ArrayList<PokeTotal>());
                detailsByPokemon.put(pokeDetails.getName(), new PokeDetails());

                for (Stats stats : pokeDetails.getStats()) {

                    PokeTotal pokeTotal = new PokeTotal(pokeDetails.getName(), stats.getStat().getName(), stats.getBaseStat());

                    if (stats.getStat().getName().equals("attack")) {
                        pokeTotalList.add(pokeTotal);
                    }
                    statsByPokemon.get(pokeDetails.getName()).add(pokeTotal);
                    detailsByPokemon.put(pokeDetails.getName(), pokeDetails);
                }
            }

            Collections.sort(pokeTotalList);

            int lastPosition = pokemons.size() - 1;
            int firstPosition = pokemons.size() - 6;

            int sumAttack = 0, sumSpeed = 0, sumSpecialDefense = 0, sumSpecialAttack = 0, sumDefense = 0, sumHP = 0, sumBaseExperience = 0, sumWeight = 0;

            for (int i = lastPosition; i >= firstPosition; i--) {
                PokeTotal pokeTotal = pokeTotalList.get(i);
                sumAttack += pokeTotal.getValue();

                pokeNames.add(new PokeTotal(pokeTotal.getPokemon()));

                for (String key : statsByPokemon.keySet()) {

                    for (int j = 0; j < statsByPokemon.get(key).size(); j++) {

                        PokeTotal pokeStatHM = statsByPokemon.get(key).get(j);

                        if (pokeTotal.getPokemon().equals(pokeStatHM.getPokemon())) {

                            if (pokeStatHM.getName().equals("speed")) {
                                sumSpeed += pokeStatHM.getValue();
                            } else if (pokeStatHM.getName().equals("special-defense")) {
                                sumSpecialDefense += pokeStatHM.getValue();
                            } else if (pokeStatHM.getName().equals("special-attack")) {
                                sumSpecialAttack += pokeStatHM.getValue();
                            } else if (pokeStatHM.getName().equals("defense")) {
                                sumDefense += pokeStatHM.getValue();
                            } else if (pokeStatHM.getName().equals("hp")) {
                                sumHP += pokeStatHM.getValue();
                            }
                        }
                    }
                }

                for (String key : detailsByPokemon.keySet()) {

                    PokeDetails pokeDetailsHM = detailsByPokemon.get(key);

                    if (pokeTotal.getPokemon().equals(pokeDetailsHM.getName())) {

                        sumBaseExperience += pokeDetailsHM.getBaseExperience();

                        sumWeight += pokeDetailsHM.getWeight();
                    }
                }
            }

            pokeTotals.add(new PokeTotal("Attack", sumAttack));
            pokeTotals.add(new PokeTotal("Speed", sumSpeed));
            pokeTotals.add(new PokeTotal("Special Defense", sumSpecialDefense));
            pokeTotals.add(new PokeTotal("Special Attack", sumSpecialAttack));
            pokeTotals.add(new PokeTotal("Defense", sumDefense));
            pokeTotals.add(new PokeTotal("HP", sumHP));
            pokeTotals.add(new PokeTotal("Base Experience", sumBaseExperience));
            pokeTotals.add(new PokeTotal("Weight", sumWeight));

            listGroups.add("Pokémons");
            listGroups.add("Total");

            listItemsGroup.put(listGroups.get(0), pokeNames);
            listItemsGroup.put(listGroups.get(1), pokeTotals);

            mAdapter = new PokeTotalAdapter(PokemonBestActivity.this, listGroups, listItemsGroup);

            elvStats.setAdapter(mAdapter);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<PokeDetails>> loader) {

    }
}
