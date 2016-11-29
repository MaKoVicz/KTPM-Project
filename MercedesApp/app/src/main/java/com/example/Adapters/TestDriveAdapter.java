package com.example.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.DTO.TestDrive;
import com.example.mercedesapp.R;

import java.util.List;

public class TestDriveAdapter extends ArrayAdapter<TestDrive> {

    //region Initiation
    private LayoutInflater layoutInflater;
    private Context context;
    private int resLayout;
    private List<TestDrive> testDriveList;
    //endregion

    //region Personal Methods
    public TestDriveAdapter(Context context, int resLayout, List<TestDrive> testDriveList) {
        super(context, resLayout, testDriveList);
        this.context = context;
        this.resLayout = resLayout;
        this.testDriveList = testDriveList;
        layoutInflater = layoutInflater.from(context);
    }
    //endregion

    //region Override Methods
    @Override
    public int getCount() {
        return testDriveList.size();
    }

    @Nullable
    @Override
    public TestDrive getItem(int position) {
        return testDriveList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestDriveAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(resLayout, null);
            holder = new TestDriveAdapter.ViewHolder();
            holder.testProduct = (TextView) convertView.findViewById(R.id.testdrive_list_item_testProduct_txtv);
            holder.guessName = (TextView) convertView.findViewById(R.id.testdrive_list_item_username_txtv);
            holder.registerDate = (TextView) convertView.findViewById(R.id.testdrive_list_item_registerDate_txtv);
            holder.phone = (TextView) convertView.findViewById(R.id.testdrive_list_item_phone_txtv);
            convertView.setTag(holder);
        } else {
            holder = (TestDriveAdapter.ViewHolder) convertView.getTag();
        }

        TestDrive testDrive = testDriveList.get(position);
        holder.testProduct.setText(testDrive.getTestProduct());
        holder.guessName.setText(testDrive.getName());
        holder.registerDate.setText(testDrive.getRegisterDate());
        holder.phone.setText(testDrive.getPhone());

        return convertView;
    }
    //endregion

    private static class ViewHolder {
        TextView testProduct, guessName, registerDate, phone;
    }
}
