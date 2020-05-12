package com.example.huehashop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.huehashop.R;
import com.example.huehashop.adapter.CategoryAdapter;
import com.example.huehashop.model.Loaisp;
import com.example.huehashop.model.Sanpham;
import com.example.huehashop.ultil.CheckConnection;
import com.example.huehashop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {
    private ListView lv_product_by_cate;
    private TextView toolbar_title_category;
    private ArrayList<Sanpham> arrSanPhams;
    private CategoryAdapter categoryAdapter;
    private boolean isLoading = false;
    private boolean limitData = false;
    private View footerView;
    private int cate_id = 0;
    private String cate_name = "";
    private int page = 1;
    private mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CheckConnection.haveNetworkConnection(this)) {
            setContentView(R.layout.activity_category);
            getDataIntent();
            initView();
            actionBar();
            getDataProductByCategoryFromServer(page);
            loadMoreData();
        } else {
            CheckConnection.ShowToast_Short(this, "Bạn hãy kiểm tra lại internet");
            finish();
        }
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

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("category")) {
                Loaisp loaisp = (Loaisp) intent.getSerializableExtra("category");
                cate_id = loaisp.getId();
                cate_name = loaisp.getTenloaisp();
            }
        }
    }

    private void initView() {
        toolbar_title_category = findViewById(R.id.toolbar_title_category);
        lv_product_by_cate = findViewById(R.id.lv_product_by_cate);
        lv_product_by_cate = findViewById(R.id.lv_product_by_cate);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
        // set data into line product by category
        arrSanPhams = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getApplicationContext(), arrSanPhams);
        lv_product_by_cate.setAdapter(categoryAdapter);
        // set font
        Typeface mMedium = ResourcesCompat.getFont(getApplicationContext(), R.font.mm);
        toolbar_title_category.setTypeface(mMedium);
        toolbar_title_category.setText(cate_name);
    }

    private void actionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataProductByCategoryFromServer(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url_api = Server.duongdansp + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    lv_product_by_cate.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            arrSanPhams.add(new Sanpham(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("tensp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("hinhanhsp"),
                                    jsonObject.getString("motasp"),
                                    jsonObject.getInt("idsanpham")
                            ));
                            categoryAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    lv_product_by_cate.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(cate_id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void loadMoreData() {
        lv_product_by_cate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", arrSanPhams.get(i));
                startActivity(intent);
            }
        });
        lv_product_by_cate.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotaItem) {
                if (FirstItem + VisibleItem == TotaItem && TotaItem != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    public class mHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lv_product_by_cate.addFooterView(footerView);
                    break;
                case 1:
                    getDataProductByCategoryFromServer(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
