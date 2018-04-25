package com.example.guilh.pokedex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    PokeStatAdapter adapter;
    List<String> listGroups = new ArrayList<>();
    ArrayList<PokeStat> pokeStats = new ArrayList<>();
    ArrayList<PokeStat> pokeAbilities = new ArrayList<>();
    ArrayList<PokeStat> pokeMoves = new ArrayList<>();
    HashMap<String, List<PokeStat>> listItemsGroup = new HashMap<>();
    ExpandableListView elvStats;
    ProgressBar mProgressBar;
    private TextView pokeName, pokeType;
    private ImageView pokeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_detail);
        pokeName = (TextView) findViewById(R.id.pokemon_name_text_view);
        pokeType = (TextView) findViewById(R.id.pokemon_type_text_view);
        pokeImage = (ImageView) findViewById(R.id.pokemon_image_view);

        elvStats = (ExpandableListView) findViewById(R.id.pokemon_stats_list);

        int id = getIntent().getIntExtra("ID", 0);
        requestData(id);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void requestData(final int id) {
        final PokeApiInterface apiService = ApiClient.getClient().create(PokeApiInterface.class);
        Call<PokeDetails> call = apiService.getPokeDetail(id);
        call.enqueue(new Callback<PokeDetails>() {
            @Override
            public void onResponse(Call<PokeDetails> call, Response<PokeDetails> response) {
                PokeDetails pokeDetails;

                if (response.isSuccessful()) {
                    pokeDetails = response.body();

                    pokeName.setText(pokeDetails.getName());

                    String text = "";
                    for (Types types : pokeDetails.getTypes()) {
                        text += types.getType().getName().substring(0, 1).toUpperCase().concat(types.getType().getName().substring(1)).replace("-"," ") + ", ";
                    }
                    text = text.substring(0, text.length() - 2);
                    pokeType.setText(text);

                    pokeStats.add(new PokeStat("Weight", pokeDetails.getWeight().toString()));
                    pokeStats.add(new PokeStat("Height", pokeDetails.getHeight().toString()));
                    pokeStats.add(new PokeStat("Base Experience", pokeDetails.getBaseExperience().toString()));

                    for (Stats stats : pokeDetails.getStats()) {
                        pokeStats.add(new PokeStat(stats.getStat().getName().substring(0, 1).toUpperCase().concat(stats.getStat().getName().substring(1)).replace("-", " "),stats.getBaseStat().toString()));
                    }

                    for (Abilities abilities : pokeDetails.getAbilities()) {
                        pokeAbilities.add(new PokeStat("Ability", abilities.getAbility().getName().substring(0, 1).toUpperCase().concat(abilities.getAbility().getName().substring(1)).replace("-", " ")));
                    }

                    for (Moves moves : pokeDetails.getMoves()){
                        pokeMoves.add(new PokeStat("Move", moves.getMove().getName().substring(0, 1).toUpperCase().concat(moves.getMove().getName().substring(1)).replace("-", " ")));
                    }


                    Picasso.with(DetailActivity.this)
                            .load(pokeDetails.getSprites().getImageUrl())
                            .resize(128, 128)
                            .into(pokeImage);

                    listGroups.add("Stats");
                    listGroups.add("Abilities");
                    listGroups.add("Moves");

                    listItemsGroup.put(listGroups.get(0),pokeStats);
                    listItemsGroup.put(listGroups.get(1),pokeAbilities);
                    listItemsGroup.put(listGroups.get(2),pokeMoves);

                    adapter = new PokeStatAdapter(DetailActivity.this,listGroups,listItemsGroup);

                    elvStats.setAdapter(adapter);
                    mProgressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(DetailActivity.this,"Error: " + response.errorBody(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PokeDetails> call, Throwable t) {
                Toast.makeText(DetailActivity.this,"Error: " + t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }
}
