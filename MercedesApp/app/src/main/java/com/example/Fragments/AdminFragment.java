package com.example.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mercedesapp.AdminProductCategoryListActivity;
import com.example.mercedesapp.AdminProductListActivity;
import com.example.mercedesapp.R;
import com.example.mercedesapp.TestDriveListActivity;
import com.example.mercedesapp.UserListActivity;

public class AdminFragment extends Fragment {

    private LinearLayout productCategoryOption, productOption, userOption, testdriveOption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment, container, false);
        productCategoryOption = (LinearLayout) view.findViewById(R.id.admin_product_category_option);
        productOption = (LinearLayout) view.findViewById(R.id.admin_product_option);
        userOption = (LinearLayout) view.findViewById(R.id.admin_user_option);
        testdriveOption = (LinearLayout) view.findViewById(R.id.admin_test_drive_option);

        setOptionClickListener();
        return view;
    }

    public void setOptionClickListener() {
        productCategoryOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminProductCategoryListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        productOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminProductListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        userOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        testdriveOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestDriveListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
