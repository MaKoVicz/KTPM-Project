package com.example.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.DTO.User;
import com.example.mercedesapp.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter implements Filterable {

    //region Initiation
    private LayoutInflater layoutInflater;
    private Context context;
    private int resLayout;
    private UserFilter userFilter = new UserFilter();
    private List<User> userList;
    private List<User> filterList;
    //endregion

    //region Personal Methods
    public UserAdapter(Context context, int resLayout, List<User> userList) {
        this.context = context;
        this.resLayout = resLayout;
        this.userList = userList;
        filterList = userList;
    }
    //endregion

    //region Override Methods
    @Override
    public int getCount() {
        return filterList.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserAdapter.ViewHolder holder;
        final User user = getItem(position);

        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resLayout, null);
            holder = new UserAdapter.ViewHolder();
            holder.guessName = (TextView) convertView.findViewById(R.id.user_list_item_name_txtv);
            holder.phone = (TextView) convertView.findViewById(R.id.user_list_item_phone_txtv);
            holder.dob = (TextView) convertView.findViewById(R.id.user_list_item_dob_txtv);
            holder.email = (TextView) convertView.findViewById(R.id.user_list_item_email_txtv);
            convertView.setTag(holder);
        } else {
            holder = (UserAdapter.ViewHolder) convertView.getTag();
        }


        holder.guessName.setText(user.getName());
        holder.phone.setText(user.getPhone());
        holder.dob.setText(user.getDob());
        holder.email.setText(user.getEmail());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }
    //endregion

    //region Private Classes
    private static class ViewHolder {
        TextView guessName, email, dob, phone;
    }

    private class UserFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<User> tempList = new ArrayList<>();

                // search content in test drive list
                for (User user : userList) {
                    if (user.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = userList.size();
                filterResults.values = userList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (List<User>) results.values;
            notifyDataSetChanged();
        }
    }
    //endregion
}
