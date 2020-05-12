package com.example.huehashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huehashop.R;
import com.example.huehashop.activity.ChiTietSanPhamActivity;
import com.example.huehashop.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHoder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @Override
    public ItemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHoder(LayoutInflater.from(context).inflate(R.layout.dong_sanphammoinhat,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        String str = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(sanpham.getGiasanpham());
        holder.txtgiasanpham.setText(str);
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHoder extends RecyclerView.ViewHolder{
        ImageView imghinhsanpham;
        TextView txttensanpham, txtgiasanpham;

        public ItemHoder(View itemView){
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham = itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham = itemView.findViewById((R.id.textviewtensanpham));
            // set font
            Typeface mMedium = ResourcesCompat.getFont(context, R.font.mm);
            txtgiasanpham.setTypeface(mMedium);
            txttensanpham.setTypeface(mMedium);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham", arraysanpham.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
