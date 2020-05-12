package com.example.huehashop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.huehashop.R;

public class ThongTinActivity extends AppCompatActivity {
    Toolbar toolbarthongtin;
    TextView txtshop, txtdiachi;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        ActionToolBar();
    }

    private void ActionToolBar() {
        toolbarthongtin = findViewById(R.id.toolbarthongtin);
        setSupportActionBar(toolbarthongtin);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarthongtin.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbarthongtin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
