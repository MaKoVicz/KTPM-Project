package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.DTO.Product;
import com.example.mercedesapp.R;

import java.util.List;

public class AdminProductAdapter extends BaseAdapter {

    //region Initiation
    private List<Product> productList;
    private Context context;
    private int resLayout;
    //endregion

    //region Personal Methods
    public AdminProductAdapter(Context context, int resLayout, List<Product> productList) {
        this.context = context;
        this.resLayout = resLayout;
        this.productList = productList;
    }
    //endregion

    //region Override Methods
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AdminProductAdapter.ViewHolder holder;
        final Product product = (Product) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resLayout, null);
            holder = new AdminProductAdapter.ViewHolder();
            holder.productName = (TextView) convertView.findViewById(R.id.admin_product_name_txtv);
            convertView.setTag(holder);
        } else {
            holder = (AdminProductAdapter.ViewHolder) convertView.getTag();
        }

        holder.productName.setText(product.getName());

        return convertView;
    }
    //endregion

    //region Private Classes
    private static class ViewHolder {
        TextView productName;
    }
    //endregion
}
