package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.DTO.ProductCategory;
import com.example.mercedesapp.R;

import java.util.List;

public class AdminProductCategoryAdapter extends BaseAdapter {

    //region Initiation
    private Context context;
    private int resLayout;
    private List<ProductCategory> categoryList;
    //endregion

    //region Personal Methods
    public AdminProductCategoryAdapter(Context context, int resLayout, List<ProductCategory> categoryList) {
        this.context = context;
        this.resLayout = resLayout;
        this.categoryList = categoryList;
    }
    //endregion

    //region Override Methods
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AdminProductCategoryAdapter.ViewHolder holder;
        final ProductCategory productCategory = (ProductCategory) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resLayout, null);
            holder = new AdminProductCategoryAdapter.ViewHolder();
            holder.productCategoryName = (TextView) convertView.findViewById(R.id.admin_product_category_name_txtv);
            convertView.setTag(holder);
        } else {
            holder = (AdminProductCategoryAdapter.ViewHolder) convertView.getTag();
        }

        holder.productCategoryName.setText(productCategory.getName());

        return convertView;
    }
    //endregion

    //region Private Classes
    private static class ViewHolder {
        TextView productCategoryName;
    }
    //endregion
}
