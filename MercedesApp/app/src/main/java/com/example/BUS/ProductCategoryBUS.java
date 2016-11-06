package com.example.BUS;

import android.content.Context;

import com.example.DAO.MercedesDB;
import com.example.DTO.ProductCategory;

import java.util.ArrayList;

public class ProductCategoryBUS {
    private Context context;

    public ProductCategoryBUS(Context context) {
        this.context = context;
    }

    public ArrayList<ProductCategory> getProductCategoryData() {
        return new MercedesDB(context).getProductCategoryData();
    }
}
