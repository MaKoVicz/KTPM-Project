package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.DTO.Product;
import com.example.mercedesapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminProductAdapter extends BaseAdapter implements Filterable {

    //region Initiation
    private List<Product> productList;
    private Context context;
    private int resLayout;
    private List<Product> filterList;
    private AdminProductFilter adminProductFilter = new AdminProductFilter();
    //endregion

    //region Personal Methods
    public AdminProductAdapter(Context context, int resLayout, List<Product> productList) {
        this.context = context;
        this.resLayout = resLayout;
        this.productList = productList;
        this.filterList = productList;
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

    @Override
    public Filter getFilter() {
        return adminProductFilter;
    }
    //endregion

    //region Private Classes
    private static class ViewHolder {
        TextView productName;
    }

    private class AdminProductFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Product> tempList = new ArrayList<>();

                // search content in test drive list
                for (Product product : productList) {
                    if (product.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(product);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = productList.size();
                filterResults.values = productList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (List<Product>) results.values;
            notifyDataSetChanged();
        }
    }
    //endregion
}
