package com.example.guilh.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class PokeStatAdapter extends BaseExpandableListAdapter {

    private List<String> listGroups;
    private HashMap<String, List<PokeStat>> listItemsGroup;
    private Context context;

    public PokeStatAdapter(Context context, List<String> groups, HashMap<String, List<PokeStat>> itemsGroup) {
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
            convertView = layoutInflater.inflate(R.layout.group_item, null);
        }

        TextView tvStat = (TextView) convertView.findViewById(R.id.stat_text_view);
        TextView tvValue = (TextView) convertView.findViewById(R.id.value_text_view);

        PokeStat pokeStat = (PokeStat) getChild(groupPosition, childPosition);

        tvStat.setText(pokeStat.getStatName());
        tvStat.setPadding(16,4, 16,0);

        tvValue.setText(pokeStat.getStatValue());
        tvValue.setPadding(16,4, 16,0);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
