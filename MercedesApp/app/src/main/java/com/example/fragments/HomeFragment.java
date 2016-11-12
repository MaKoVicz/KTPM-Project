package com.example.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.Adapters.ProductCategoryAdapter;
import com.example.BUS.ProductCategoryBUS;
import com.example.DTO.ProductCategory;
import com.example.mercedesapp.ProductListActivity;
import com.example.mercedesapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<ProductCategory> productCategories;
    private ListView categoryList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        categoryList = (ListView) view.findViewById(R.id.product_category_list);
        setupCategoryList();
        setListItemClickListener();
        return view;
    }

    public void setupCategoryList() {
        productCategories = new ArrayList<ProductCategory>();
        productCategories = new ProductCategoryBUS(getActivity()).getProductCategoryData();

        ProductCategoryAdapter productCategoryAdapter =
                new ProductCategoryAdapter(getActivity(), R.layout.product_category_list_item, productCategories);
        categoryList.setAdapter(productCategoryAdapter);
    }

    public void setListItemClickListener() {
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("Category", productCategories.get(position).getName());
                startActivity(intent);
            }
        });
    }
}