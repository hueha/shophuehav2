package com.example.huehashop.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.huehashop.R;
import com.example.huehashop.activity.GioHangActivity;
import com.example.huehashop.activity.MainActivity;
import com.example.huehashop.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

//    public void setData(ArrayList<Giohang> listData){
//        arraygiohang.clear();
//        arraygiohang.addAll(listData);
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arraygiohang.get(i);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txttengiohang, txtgiagiohang;
        public ImageView imggiohang;
        public ImageButton btnminus, btnplus;
        public Button btnvalues;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.txttengiohang = view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = view.findViewById(R.id.imageviewgiohnag);
            viewHolder.btnminus = view.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = view.findViewById(R.id.buttonvalues);
            viewHolder.btnplus = view.findViewById(R.id.buttonplus);

            Typeface mMedium = ResourcesCompat.getFont(context, R.font.mm);
            viewHolder.txttengiohang.setTypeface(mMedium);
            viewHolder.txtgiagiohang.setTypeface(mMedium);
            viewHolder.btnvalues.setTypeface(mMedium);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Giohang giohang = (Giohang) getItem(i);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        final String gia = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(giohang.getGiasp());
        viewHolder.txtgiagiohang.setText("Giá: " + gia);
        Picasso.with(context).load(giohang.getHinhsp()).into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(giohang.getSoluongsp() + "");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (sl >= 10) {
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        viewHolder.btnvalues.setText(giohang.getSoluongsp() + "");
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) + 1;
                int slhientai = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                finalViewHolder.txtgiagiohang.setText("Giá: " + gia);
                GioHangActivity.EventUltil();
                if (slmoinhat > 9) {
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) - 1;
                int slht = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                finalViewHolder.txtgiagiohang.setText("Giá: " + gia);
                GioHangActivity.EventUltil();
                if (slmoinhat < 2) {
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });

        return view;
    }
}
