package com.example.guilh.pokedex;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<List<Pokemon>> {

    PokemonAdapter mAdapter;
    ListView listView;
    View footer;
    ProgressBar progressBar;
    private Handler mHandler;
    private int offset;
    private TextView mEmpityStateTextView;
    private boolean hasCallback;
    private Runnable showMore = new Runnable() {
        @Override
        public void run() {
            boolean noMoreToShow = mAdapter.showMore();
            offset += 20;
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(1, null, MainActivity.this);
            hasCallback = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        listView = (ListView) findViewById(R.id.pokemon_list);
        footer = getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progress_bar);


        mAdapter = new PokemonAdapter(this, new ArrayList<Pokemon>(), 20, 20);
        listView.setAdapter(mAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        mEmpityStateTextView = (TextView) findViewById(R.id.empity_view);
        listView.setEmptyView(mEmpityStateTextView);
        listView.setOnScrollListener(this);

        if (networkInfo != null && networkInfo.isConnected()) {
            offset = 0;
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else {
            View loadingIndicator = (ProgressBar) findViewById(R.id.progress_bar_main);
            loadingIndicator.setVisibility(View.GONE);

            mEmpityStateTextView.setText(R.string.no_internet_connection);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pokemon pokemon = mAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ID", pokemon.getNumber());
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<Pokemon>> onCreateLoader(int id, Bundle args) {
        return new PokemonLoader(this, offset);
    }

    @Override
    public void onLoadFinished(Loader<List<Pokemon>> loader, List<Pokemon> data) {
        View loadingIndicator = (ProgressBar) findViewById(R.id.progress_bar_main);
        loadingIndicator.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            listView.removeFooterView(footer);
            progressBar.setVisibility(View.GONE);
            mAdapter.addAll(data);
            listView.addFooterView(footer);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            mEmpityStateTextView.setText(R.string.no_pokemon);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Pokemon>> loader) {
        mAdapter.clear();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && !mAdapter.endReached() && !hasCallback) {
            mHandler.postDelayed(showMore, 300);
            hasCallback = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_best_pokemon:
                Intent intent = new Intent(MainActivity.this, PokemonBestActivity.class);
                if (offset == 0) {
                    offset = 20;
                }
                intent.putExtra("AMOUNT", offset);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
