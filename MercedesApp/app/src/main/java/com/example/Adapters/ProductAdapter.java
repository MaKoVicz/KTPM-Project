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

import com.example.DTO.Product;
import com.example.mercedesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    //region Initiation
    private LayoutInflater layoutInflater;
    private Context context;
    private int resLayout;
    private List<Product> products;
    //endregion

    //region Methods
    public ProductAdapter(Context context, int resLayout, List<Product> products) {
        super(context, resLayout, products);
        this.context = context;
        this.resLayout = resLayout;
        this.products = products;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return products.get(position);
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
            holder.title = (TextView) convertView.findViewById(R.id.product_title_item_txtv);
            holder.price = (TextView) convertView.findViewById(R.id.product_price_item_txtv);
            holder.productImg = (ImageView) convertView.findViewById(R.id.product_list_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        holder.title.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        if (holder.productImg != null) {
            Picasso.with(context)
                    .load(product.getPic1())
                    .placeholder(R.drawable.ic_vector_image_loading)
                    .into(holder.productImg);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView title, price;
        ImageView productImg;
    }
    //endregion
}
