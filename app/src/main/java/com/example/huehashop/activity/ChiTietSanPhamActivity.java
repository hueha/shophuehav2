package com.example.huehashop.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.huehashop.R;
import com.example.huehashop.model.Giohang;
import com.example.huehashop.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imgchitiet;
    TextView txtten, txtgia, txtmota, toolbar_title_detail, tv_detail_product;
    Spinner spinner;
    Button btndatmua;
    int id = 0;
    String TenChiTiet = "";
    int GiaChitiet = 0;
    String HinhanhChitiet = "";
    String MotaChitiet = "";
    int Idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetInformtion();
        CatchEventSpinner();
        EventButton();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.manggiohang.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                        if (MainActivity.manggiohang.get(i).getIdsp() == id) {
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluongsp() >= 10) {
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * GiaChitiet;
                        MainActivity.manggiohang.add(new Giohang(id, TenChiTiet, Giamoi, HinhanhChitiet, soluong));

                    }
                } else {

                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong + GiaChitiet;
                    MainActivity.manggiohang.add(new Giohang(id, TenChiTiet, Giamoi, HinhanhChitiet, soluong));


                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformtion() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        TenChiTiet = sanpham.getTensanpham();
        HinhanhChitiet = sanpham.getHinhanhsanpham();
        GiaChitiet = sanpham.getGiasanpham();
        MotaChitiet = sanpham.getMotasanpham();
        Idsanpham = sanpham.getIDSanpham();

        txtten.setText(TenChiTiet);
        txtgia.setText("Giá: " + NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(GiaChitiet));
        txtmota.setText(TenChiTiet + "\n" + MotaChitiet);
        Picasso.with(ChiTietSanPhamActivity.this).load(HinhanhChitiet).into(imgchitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet = findViewById(R.id.toolbarchitietsanpham);
        toolbar_title_detail = findViewById(R.id.toolbar_title_detail);
        tv_detail_product = findViewById(R.id.tv_detail_product);
        imgchitiet = findViewById(R.id.imgaviewchitietsanpham);
        txtten = findViewById(R.id.textviewchitietsanpham);
        txtgia = findViewById(R.id.textviewgiasanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.buttondatmua);

        Typeface mMedium = ResourcesCompat.getFont(getApplicationContext(), R.font.mm);

        toolbar_title_detail.setTypeface(mMedium);
        txtgia.setTypeface(mMedium);
        txtten.setTypeface(mMedium);
        tv_detail_product.setTypeface(mMedium);
        txtmota.setTypeface(mMedium);
        btndatmua.setTypeface(mMedium);
    }


}
