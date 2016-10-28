package com.example.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.DTO.NavItem;
import com.example.mercedesapp.R;

import java.util.List;

public class NavListAdapter extends ArrayAdapter<NavItem> {

    //region Initiation
    private Context context;
    private int resLayout;
    private List<NavItem> navListItems;
    //endregion

    public NavListAdapter(Context context, int resLayout, List<NavItem> navListItems) {
        super(context, resLayout, navListItems);

        this.context = context;
        this.resLayout = resLayout;
        this.navListItems = navListItems;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, resLayout, null);
        TextView title = (TextView) view.findViewById(R.id.nav_list_title);
        ImageView icon = (ImageView) view.findViewById(R.id.nav_list_icon);

        title.setText(navListItems.get(position).getTitle());
        icon.setImageResource(navListItems.get(position).getResIcon());

        return view;
    }
}
