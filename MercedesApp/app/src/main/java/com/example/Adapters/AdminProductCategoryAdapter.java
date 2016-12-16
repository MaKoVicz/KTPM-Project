package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.DTO.ProductCategory;
import com.example.mercedesapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminProductCategoryAdapter extends BaseAdapter implements Filterable {

    //region Initiation
    private Context context;
    private int resLayout;
    private List<ProductCategory> categoryList;
    private List<ProductCategory> filterList;
    private AdminProductCategoryFilter adminProductCategoryFilter = new AdminProductCategoryFilter();
    //endregion

    //region Personal Methods
    public AdminProductCategoryAdapter(Context context, int resLayout, List<ProductCategory> categoryList) {
        this.context = context;
        this.resLayout = resLayout;
        this.categoryList = categoryList;
        this.filterList = categoryList;
    }
    //endregion

    //region Override Methods
    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterList.get(position);
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

    @Override
    public Filter getFilter() {
        return adminProductCategoryFilter;
    }
    //endregion

    //region Private Classes
    private static class ViewHolder {
        TextView productCategoryName;
    }

    private class AdminProductCategoryFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<ProductCategory> tempList = new ArrayList<>();

                // search content in test drive list
                for (ProductCategory productCategory : categoryList) {
                    if (productCategory.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(productCategory);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = categoryList.size();
                filterResults.values = categoryList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (List<ProductCategory>) results.values;
            notifyDataSetChanged();
        }
    }
    //endregion
}
