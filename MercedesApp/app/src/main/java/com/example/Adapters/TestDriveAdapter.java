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

import com.example.DTO.TestDrive;
import com.example.mercedesapp.R;

import java.util.ArrayList;
import java.util.List;

public class TestDriveAdapter extends BaseAdapter implements Filterable {

    //region Initiation
    private LayoutInflater layoutInflater;
    private Context context;
    private int resLayout;
    private TestDriveFilter testDriveFilter = new TestDriveFilter();
    private List<TestDrive> testDriveList;
    private List<TestDrive> filterList;
    //endregion

    //region Personal Methods
    public TestDriveAdapter(Context context, int resLayout, List<TestDrive> testDriveList) {
        this.context = context;
        this.resLayout = resLayout;
        this.testDriveList = testDriveList;
        this.filterList = testDriveList;
    }
    //endregion

    //region Override Methods
    @Override
    public int getCount() {
        return filterList.size();
    }

    @Nullable
    @Override
    public TestDrive getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TestDriveAdapter.ViewHolder holder;
        final TestDrive testDrive = getItem(position);

        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resLayout, parent, false);
            holder = new TestDriveAdapter.ViewHolder();
            holder.testProduct = (TextView) convertView.findViewById(R.id.testdrive_list_item_testProduct_txtv);
            holder.guessName = (TextView) convertView.findViewById(R.id.testdrive_list_item_username_txtv);
            holder.registerDate = (TextView) convertView.findViewById(R.id.testdrive_list_item_registerDate_txtv);
            holder.phone = (TextView) convertView.findViewById(R.id.testdrive_list_item_phone_txtv);
            convertView.setTag(holder);
        } else {
            holder = (TestDriveAdapter.ViewHolder) convertView.getTag();
        }

        holder.testProduct.setText(testDrive.getTestProduct());
        holder.guessName.setText(testDrive.getName());
        holder.registerDate.setText(testDrive.getRegisterDate());
        holder.phone.setText(testDrive.getPhone());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return testDriveFilter;
    }
    //endregion

    //region Private Classes
    private static class ViewHolder {
        TextView testProduct, guessName, registerDate, phone;
    }

    private class TestDriveFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<TestDrive> tempList = new ArrayList<>();

                // search content in test drive list
                for (TestDrive testDrive : testDriveList) {
                    if (testDrive.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(testDrive);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = testDriveList.size();
                filterResults.values = testDriveList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (List<TestDrive>) results.values;
            notifyDataSetChanged();
        }
    }
    //endregion
}
