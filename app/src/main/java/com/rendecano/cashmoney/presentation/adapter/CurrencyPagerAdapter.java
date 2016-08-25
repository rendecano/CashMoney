package com.rendecano.cashmoney.presentation.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rendecano.cashmoney.R;
import com.rendecano.cashmoney.data.entity.Rate;

import java.util.List;

/**
 * Created by Ren Decano.
 */

public class CurrencyPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Rate> mRates;

    public CurrencyPagerAdapter(Context context, List<Rate> rates) {
        mContext = context;
        this.mRates = rates;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_rate_item, collection, false);

        TextView txtCurrency = (TextView) layout.findViewById(R.id.txtCurrency);
        txtCurrency.setText(mRates.get(position).getCurrency());

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mRates.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRates.get(position).getCurrency();
    }
}
