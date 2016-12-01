package com.example.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.DTO.User;
import com.example.mercedesapp.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    //region Initiation
    private LayoutInflater layoutInflater;
    private Context context;
    private int resLayout;
    private List<User> userList;
    //endregion

    //region Personal Methods
    public UserAdapter(Context context, int resLayout, List<User> userList) {
        super(context, resLayout, userList);
        this.context = context;
        this.resLayout = resLayout;
        this.userList = userList;
        layoutInflater = layoutInflater.from(context);
    }
    //endregion

    //region Override Methods
    @Override
    public int getCount() {
        return userList.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserAdapter.ViewHolder holder;

        if (convertView == null) {
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

        User user = userList.get(position);
        holder.guessName.setText(user.getName());
        holder.phone.setText(user.getPhone());
        holder.dob.setText(user.getDob());
        holder.email.setText(user.getEmail());

        return convertView;
    }
    //endregion

    private static class ViewHolder {
        TextView guessName, email, dob, phone;
    }
}
