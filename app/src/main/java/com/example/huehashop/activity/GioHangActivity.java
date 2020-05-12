package com.example.huehashop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.huehashop.R;
import com.example.huehashop.adapter.GiohangAdapter;
import com.example.huehashop.model.Giohang;
import com.example.huehashop.ultil.CheckConnection;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GioHangActivity extends AppCompatActivity {
    ListView lvgiohang;
    static TextView txttongtien;
    TextView toolbar_title_detail, txtthongbao;
    Button btnthanhtoan;
    static Button btntieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        ActionToolbar();
        EventUltil();
        CactOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), Thongtinkhachhang.class);
                    startActivity(intent);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }

    private void CactOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("onItemLongClick", "onItemLongClick: ");
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this)
                        .setTitle("Xác nhận xóa sản phẩm")
                        .setMessage("Bạn có chắc muốn xóa sản phẩm này")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (MainActivity.manggiohang.size() <= 0) {
                                    txtthongbao.setVisibility(View.VISIBLE);
                                } else {
                                    MainActivity.manggiohang.remove(position);
                                    giohangAdapter.notifyDataSetChanged();
                                    EventUltil();
                                    if (MainActivity.manggiohang.size() <= 0) {
                                        txtthongbao.setVisibility(View.VISIBLE);
                                    } else {
                                        txtthongbao.setVisibility(View.INVISIBLE);
                                        giohangAdapter.notifyDataSetChanged();
                                        EventUltil();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                giohangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        int tongtien = 0;
        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        txttongtien.setText("Tổng tiền: " + NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongtien));

//        btntieptucmua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
//        });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbargiohang.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Anhxa() {
        lvgiohang = findViewById(R.id.listviewgiohang);
        toolbar_title_detail = findViewById(R.id.toolbar_title_detail);
        txttongtien = findViewById(R.id.textviewtongtien);
        txtthongbao = findViewById(R.id.txtthongbao);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = findViewById(R.id.buttontieptucmuahang);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(this, MainActivity.manggiohang); // chỗ này chuyền lít bị sai
        lvgiohang.setAdapter(giohangAdapter);

        Typeface mMedium = ResourcesCompat.getFont(getApplicationContext(), R.font.mm);
        btntieptucmua.setTypeface(mMedium);
        btnthanhtoan.setTypeface(mMedium);
        txttongtien.setTypeface(mMedium);
        txtthongbao.setTypeface(mMedium);
        toolbar_title_detail.setTypeface(mMedium);
    }
}
