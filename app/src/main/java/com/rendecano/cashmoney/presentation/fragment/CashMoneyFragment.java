package com.rendecano.cashmoney.presentation.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rendecano.cashmoney.R;
import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.presentation.adapter.CurrencyPagerAdapter;
import com.rendecano.cashmoney.presentation.presenter.CashMoneyPresenter;
import com.rendecano.cashmoney.presentation.presenter.view.CashMoneyView;

public class CashMoneyFragment extends Fragment implements CashMoneyView, ViewPager.OnPageChangeListener {

    private View mView;
    private TextView mTxtBase;
    private TextView mTxtRate;
    private EditText mEtConvert;
    private CashMoneyPresenter mPresenter;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_cash_money, container, false);

        mTxtBase = (TextView) mView.findViewById(R.id.txtBase);
        mTxtRate = (TextView) mView.findViewById(R.id.txtRate);
        mEtConvert = (EditText) mView.findViewById(R.id.etConvert);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);

        mViewPager.setOnPageChangeListener(this);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter = new CashMoneyPresenter();
        mPresenter.attachView(this);
        mPresenter.initialize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void initialValues(Base base) {
        mTxtBase.setText(base.getBase());

        mViewPager.setAdapter(new CurrencyPagerAdapter(getActivity(), base.getRates()));

    }

    @Override
    public void showConvertedRate(String pValue) {
        mTxtRate.setText(pValue);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        String currency = mViewPager.getAdapter().getPageTitle(position).toString();

        Toast.makeText(getActivity(), currency, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
