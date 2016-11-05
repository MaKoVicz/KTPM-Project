package com.example.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.DTO.ProductCategory;
import com.example.mercedesapp.R;

import java.util.List;

public class ProductCategoryAdapter extends ArrayAdapter<ProductCategory> {

    //region Initiation
    private Context context;
    private int resLayout;
    private List<ProductCategory> productCategories;
    //endregion

    public ProductCategoryAdapter(Context context, int resLayout, List<ProductCategory> productCategories) {
        super(context, resLayout, productCategories);
        this.context = context;
        this.resLayout = resLayout;
        this.productCategories = productCategories;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, resLayout, null);
        TextView title = (TextView) view.findViewById(R.id.category_list_title);
        title.setText(productCategories.get(position).getName());

        return view;
    }
}