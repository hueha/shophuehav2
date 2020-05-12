package com.example.huehashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.huehashop.R;
import com.example.huehashop.ultil.CheckConnection;
import com.example.huehashop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
    EditText edttenkhachhang, edtemail, edtsdt;
    Button btnxacnhan, btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        AnhXa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edttenkhachhang.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                final String email = edtemail.getText().toString().trim();

                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            final int a = Integer.parseInt(String.valueOf(madonhang.trim()));
                            test(a);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", ten);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("email", email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void test(final int madonhang) {
        if (madonhang > 0) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST, Server.duongdanchitietdonhang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String s = String.valueOf(response.charAt(0));

                    if (s.equals("1")) {
                        MainActivity.manggiohang.clear();
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn đã thêm dữ liệu giỏ hàng thành công");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Mời quý khách tiếp tục mua hàng");

                    } else {
                        Log.d("giohang", "onResponse: " + MainActivity.manggiohang.size());
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Dữ liệu giỏ hàng đã bị lỗi !! Vui lòng kiểm tra lại");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("madonhang", madonhang + "");
                            jsonObject.put("masanpham", MainActivity.manggiohang.get(i).getIdsp());
                            jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                            jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
                            jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(jsonObject);
                    }
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("json", jsonArray.toString());
                    Log.d("json123", "" + hashMap.get("json"));
                    return hashMap;
                }
            };
            queue.add(request);
        }
    }

    private void AnhXa() {
        edttenkhachhang = findViewById(R.id.edittexttenkhachhang);
        edtemail = findViewById(R.id.edittextemailkhachhang);
        edtsdt = findViewById(R.id.edittextsodienthoaikhachhang);
        btntrove = findViewById(R.id.buttontrove);
        btnxacnhan = findViewById(R.id.buttonxacnhan);
    }
}



