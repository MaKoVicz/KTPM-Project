package com.example.BUS;

import android.content.Context;
import android.widget.Toast;

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

    public ArrayList<Product> getAllProductData() {
        return new MercedesDB(context).getAllProductData();
    }

    public Product getProductDetailInformation(String name) {
        return new MercedesDB(context).getProductDetailInformation(name);
    }

    public boolean addProductData(Product product) {
        product.setName(product.getName().trim());
        product.setColor(product.getColor().trim());
        product.setPrice(product.getPrice().trim());
        product.setCategory(product.getCategory().trim());
        product.setDescription(product.getDescription().trim());
        product.setPic1(product.getPic1().trim());

        if (!checkValidPrice(product.getPrice())) {
            Toast.makeText(context, "Price was not in a correct format", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check product name exist
        ArrayList<Product> products = new MercedesDB(context).getAllProductData();
        for (Product i : products) {
            if (i.getName().toLowerCase().equals(product.getName().toLowerCase())) {
                Toast.makeText(context, product.getName() + " already exist", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return new MercedesDB(context).addProductData(product);
    }

    public boolean updateProductData(String name, Product product) {
        product.setName(product.getName().trim());
        product.setColor(product.getColor().trim());
        product.setPrice(product.getPrice().trim());
        product.setCategory(product.getCategory().trim());
        product.setDescription(product.getDescription().trim());
        product.setPic1(product.getPic1().trim());

        if (!checkValidPrice(product.getPrice())) {
            Toast.makeText(context, "Price was not in a correct format", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!name.equals(product.getName())) { //if update category name
            ArrayList<Product> products = new MercedesDB(context).getAllProductData();

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                    products.get(i).setName(product.getName());
                }
            }

            int counter = 0;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getName().toLowerCase().equals(product.getName().toLowerCase())) {
                    counter++;
                }
            }

            if (counter > 1) { //if product name exist, cancel
                Toast.makeText(context, product.getName() + " already exist", Toast.LENGTH_SHORT).show();
                return false;
            } else { //if product name not exist, update product
                return new MercedesDB(context).updateProductData(name, product);
            }
        }

        //if not update product name, so just update normally
        return new MercedesDB(context).updateProductData(name, product);
    }

    public boolean updateProductWhenDeleteCategory(String categoryName) {
        return new MercedesDB(context).updateProductWhenDeleteCategory(categoryName);
    }

    public boolean deleteProductData(String name) {
        return new MercedesDB(context).deleteProductData(name);
    }

    public boolean checkValidPrice(String priceString) {
        try {
            Double.parseDouble(priceString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
