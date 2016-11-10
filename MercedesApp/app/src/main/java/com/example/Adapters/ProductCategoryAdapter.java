package com.example.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.DTO.ProductCategory;
import com.example.mercedesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductCategoryAdapter extends ArrayAdapter<ProductCategory> {

    //region Initiation
    private LayoutInflater layoutInflater;
    private Context context;
    private int resLayout;
    private List<ProductCategory> productCategories;
    //endregion

    //region Methods
    public ProductCategoryAdapter(Context context, int resLayout, List<ProductCategory> productCategories) {
        super(context, resLayout, productCategories);
        this.context = context;
        this.resLayout = resLayout;
        this.productCategories = productCategories;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productCategories.size();
    }

    @Nullable
    @Override
    public ProductCategory getItem(int position) {
        return productCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(resLayout, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.category_list_title);
            holder.categoryImg = (ImageView) convertView.findViewById(R.id.category_list_img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductCategory productCategory = productCategories.get(position);
        holder.title.setText(productCategory.getName());
        if (holder.categoryImg != null) {
            Picasso.with(context)
                    .load(productCategory.getImageURL())
                    .placeholder(R.drawable.ic_vector_image_loading)
                    .into(holder.categoryImg);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        ImageView categoryImg;
    }
    //endregion
}