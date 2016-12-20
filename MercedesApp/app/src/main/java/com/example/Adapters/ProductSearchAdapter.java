package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.DTO.Product;
import com.example.mercedesapp.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchAdapter extends BaseAdapter implements Filterable {

    //region Initiation
    private Context context;
    private AdminProductFilter adminProductFilter = new AdminProductFilter();
    private int resLayout;
    private List<Product> productList;
    private List<Product> filterList;
    //endregion

    //region Personal Methods
    public ProductSearchAdapter(Context context, int resLayout, List<Product> productList) {
        this.context = context;
        this.resLayout = resLayout;
        this.productList = productList;
        this.filterList = productList;
    }

    public String formatPriceText(String price) {
        long money = Long.parseLong(price);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);

        if (moneyString.endsWith(".00")) {
            int centsIndex = moneyString.lastIndexOf(".00");
            if (centsIndex != -1) {
                moneyString = moneyString.substring(1, centsIndex);
            }
        }

        return moneyString + " VNĐ";
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
        final ProductSearchAdapter.ViewHolder holder;
        final Product product = (Product) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resLayout, null);
            holder = new ProductSearchAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.product_title_item_txtv);
            holder.price = (TextView) convertView.findViewById(R.id.product_price_item_txtv);
            holder.productImg = (ImageView) convertView.findViewById(R.id.product_list_img);
            convertView.setTag(holder);
        } else {
            holder = (ProductSearchAdapter.ViewHolder) convertView.getTag();
        }

        holder.title.setText(product.getName());
        holder.price.setText(formatPriceText(product.getPrice()));
        if (holder.productImg != null) {
            Picasso.with(context)
                    .load(product.getPic1())
                    .placeholder(R.drawable.ic_vector_image_loading)
                    .into(holder.productImg);
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return adminProductFilter;
    }
    //endregion

    //region Private Classes
    private static class ViewHolder {
        TextView title, price;
        ImageView productImg;
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
                filterResults.count = 0;
                filterResults.values = new ArrayList<Product>();
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
