package com.example.guilh.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class PokeTotalAdapter extends BaseExpandableListAdapter {

    private List<String> listGroups;
    private Map<String, List<PokeTotal>> listItemsGroup;
    private Context context;

    public PokeTotalAdapter(Context context, List<String> groups, Map<String, List<PokeTotal>> itemsGroup) {
        this.context = context;
        listGroups = groups;
        listItemsGroup = itemsGroup;
    }

    @Override
    public int getGroupCount() {
        return listGroups.size();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return listItemsGroup.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItemsGroup.get(getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group, null);
        }

        TextView tvGroup = (TextView) convertView.findViewById(R.id.tv_group);

        tvGroup.setText(getGroup(groupPosition).toString());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_item_total, null);
        }

        TextView tvStat = (TextView) convertView.findViewById(R.id.stat_total_text_view);
        TextView tvPokemon = (TextView) convertView.findViewById(R.id.pokemon_total_text_view);
        TextView tvValue = (TextView) convertView.findViewById(R.id.value_total_text_view);

        PokeTotal pokeTotal = (PokeTotal) getChild(groupPosition, childPosition);

        if (pokeTotal.hasPokemon()) {
            tvPokemon.setText(pokeTotal.getPokemon());
            tvPokemon.setVisibility(View.VISIBLE);
            tvPokemon.setPadding(16, 4, 16, 0);

            tvStat.setVisibility(View.GONE);
            tvValue.setVisibility(View.GONE);
        } else {
            tvStat.setText(pokeTotal.getName());
            tvValue.setText(pokeTotal.getValue().toString());

            tvStat.setVisibility(View.VISIBLE);
            tvPokemon.setVisibility(View.GONE);
            tvValue.setVisibility(View.VISIBLE);

            tvStat.setPadding(16,4, 16,0);
            tvValue.setPadding(16,4, 16,0);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
