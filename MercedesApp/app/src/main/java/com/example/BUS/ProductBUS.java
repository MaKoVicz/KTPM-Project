package com.example.BUS;

import android.content.Context;

import com.example.DAO.MercedesDB;
import com.example.DTO.Product;

import java.util.ArrayList;

public class ProductBUS {
    private Context context;

    public ProductBUS(Context context) {
        this.context = context;
    }

    public ArrayList<Product> getProductData(String category) {
        return new MercedesDB(context).getProductData(category);
    }
}
