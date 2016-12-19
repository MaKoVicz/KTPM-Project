package com.example.BUS;

import android.content.Context;
import android.widget.Toast;

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

    public ProductCategory getProductCategoryDetailData(String name) {
        return new MercedesDB(context).getProductCategoryDetailData(name);
    }

    public boolean addProductCategoryData(ProductCategory categoryData) {
        categoryData.setName(categoryData.getName().trim());
        categoryData.setImageURL(categoryData.getImageURL().trim());

        ArrayList<ProductCategory> productCategories =
                new MercedesDB(context).getProductCategoryData();

        //check category exist
        for (ProductCategory productCategory : productCategories) {
            if (productCategory.getName().toLowerCase().equals(categoryData.getName().toLowerCase())) {
                Toast.makeText(context, categoryData.getName() + " category already exist", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return new MercedesDB(context).addProductCategoryData(categoryData);
    }

    public boolean updateProductCategoryData(String name, ProductCategory categoryData) {
        categoryData.setName(categoryData.getName().trim());
        categoryData.setImageURL(categoryData.getImageURL().trim());

        if (!name.equals(categoryData.getName())) { //if update category name
            ArrayList<ProductCategory> productCategories =
                    new MercedesDB(context).getProductCategoryData();

            for (int i = 0; i < productCategories.size(); i++) {
                if (productCategories.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                    productCategories.get(i).setName(categoryData.getName());
                }
            }

            int counter = 0;
            for (int i = 0; i < productCategories.size(); i++) {
                if (productCategories.get(i).getName().toLowerCase().equals(categoryData.getName().toLowerCase())) {
                    counter++;
                }
            }

            if (counter > 1) { //if category exist, cancel
                Toast.makeText(context, categoryData.getName() + " category already exist", Toast.LENGTH_SHORT).show();
                return false;
            } else { //if category not exist, update category
                return new MercedesDB(context).updateProductCategoryData(name, categoryData);
            }
        }

        //if not update category name, so just update normally
        return new MercedesDB(context).updateProductCategoryData(name, categoryData);
    }

    public boolean deleteProductCategoryData(String name) {
        return new MercedesDB(context).deleteProductCategoryData(name);
    }
}