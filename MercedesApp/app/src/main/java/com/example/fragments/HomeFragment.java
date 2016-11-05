package com.example.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.Adapters.ProductCategoryAdapter;
import com.example.DAO.MercedesDB;
import com.example.DTO.ProductCategory;
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
        return view;
    }

    public void setupCategoryList() {
        productCategories = new ArrayList<ProductCategory>();
        MercedesDB myDB = new MercedesDB(getActivity());

        productCategories = myDB.getProductCategoryData();
        ProductCategoryAdapter productCategoryAdapter =
                new ProductCategoryAdapter(getActivity(), R.layout.product_category_list_item, productCategories);
        categoryList.setAdapter(productCategoryAdapter);
    }
}