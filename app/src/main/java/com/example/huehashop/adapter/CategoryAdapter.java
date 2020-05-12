package com.example.huehashop.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.huehashop.R;
import com.example.huehashop.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrSanPhams;

    public CategoryAdapter(Context context, ArrayList<Sanpham> arrSanPhams) {
        this.context = context;
        this.arrSanPhams = arrSanPhams;
    }

    @Override
    public int getCount() {
        return arrSanPhams.size();
    }

    @Override
    public Object getItem(int position) {
        return arrSanPhams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null){
            holder = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.line_product_by_category, parent, false);

            holder.imv_cate = convertView.findViewById(R.id.imv_cate);
            holder.tv_name_product_by_cate = convertView.findViewById(R.id.tv_name_product_by_cate);
            holder.tv_price_product_by_cate = convertView.findViewById(R.id.tv_price_product_by_cate);
            holder.tv_des_product_by_cate = convertView.findViewById(R.id.tv_des_product_by_cate);

            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }

        Sanpham sanpham = arrSanPhams.get(position);
        holder.tv_name_product_by_cate.setText(sanpham.getTensanpham());
        holder.tv_des_product_by_cate.setText(sanpham.getMotasanpham());
        holder.tv_price_product_by_cate.setText("Giá: " + NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(sanpham.getGiasanpham()));
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.imv_cate);

        Typeface mMedium = ResourcesCompat.getFont(context, R.font.mm);
        holder.tv_name_product_by_cate.setTypeface(mMedium);
        holder.tv_price_product_by_cate.setTypeface(mMedium);
        holder.tv_des_product_by_cate.setTypeface(mMedium);

        return convertView;
    }

    public class MyViewHolder{
        public ImageView imv_cate;
        public TextView tv_name_product_by_cate, tv_price_product_by_cate, tv_des_product_by_cate;
    }
}
