package com.example.huehashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huehashop.R;

public class LienHeActivity extends AppCompatActivity {
    Toolbar toolbarlienhe;
    TextView txtname, txtemail, txtphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        ActionToolBar();

    }

    private void ActionToolBar() {
        toolbarlienhe = findViewById(R.id.toolbarlienhe);
        setSupportActionBar(toolbarlienhe);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlienhe.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbarlienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
