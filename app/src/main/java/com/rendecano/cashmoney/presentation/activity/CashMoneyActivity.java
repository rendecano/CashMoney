package com.rendecano.cashmoney.presentation.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rendecano.cashmoney.R;
import com.rendecano.cashmoney.presentation.custom.FontCache;

public class CashMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FontCache.getInstance(this).addFont("helvetica", "helvetica.ttf");
        DataBindingUtil.setContentView(this, R.layout.activity_cash_money);
    }
}
