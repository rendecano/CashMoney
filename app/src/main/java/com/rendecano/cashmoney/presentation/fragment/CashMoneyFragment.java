package com.rendecano.cashmoney.presentation.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rendecano.cashmoney.R;
import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.presentation.adapter.CurrencyPagerAdapter;
import com.rendecano.cashmoney.presentation.presenter.CashMoneyPresenter;
import com.rendecano.cashmoney.presentation.presenter.view.CashMoneyView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CashMoneyFragment extends Fragment implements CashMoneyView, ViewPager.OnPageChangeListener, TextWatcher, View.OnTouchListener {

    private View mView;
    private TextView mTxtBase;
    private TextView mTxtRate;
    private EditText mEtConvert;
    private ViewPager mViewPager;
    private ProgressDialog mProgressBar;
    private DecimalFormat mDecimalFormat;

    private CashMoneyPresenter mPresenter;
    private String mCurrency;
    private String mCurrent = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Binding for custom fonts
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash_money, container, false);
        mView = binding.getRoot();

        mTxtBase = (TextView) mView.findViewById(R.id.txtBase);
        mTxtRate = (TextView) mView.findViewById(R.id.txtRate);
        mEtConvert = (EditText) mView.findViewById(R.id.etConvert);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);

        mView.setOnTouchListener(this);

        mEtConvert.addTextChangedListener(this);
        mEtConvert.setSelection(mEtConvert.getText().length());

        // Get display width to calculate the spacing between views
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(width / 3, 0, width / 3, 0);
        mViewPager.setPageMargin(0);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setPageTransformer(false, new FadePageTransformer());

        mViewPager.setOnPageChangeListener(this);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDecimalFormat = new DecimalFormat("#,###.##");

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
        mCurrency = base.getRates().get(0).getCurrency();
        mViewPager.setAdapter(new CurrencyPagerAdapter(getActivity(), base.getRates()));

        // Convert the default value
        sendValue(mEtConvert.getText().toString());
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
        mProgressBar = ProgressDialog.show(getActivity(), "",
                "Fetching rates...", true);
    }

    @Override
    public void hideLoading() {
        if (mProgressBar != null) {
            mProgressBar.dismiss();
        }
    }

    private void sendValue(String value) {
        mPresenter.convertCurrency(value, mCurrency);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // No implementation
    }

    @Override
    public void onPageSelected(int position) {
        mCurrency = mViewPager.getAdapter().getPageTitle(position).toString();
        sendValue(mEtConvert.getText().toString());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // No implementation
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // No implementation
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // No implementation
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!s.toString().equals(mCurrent)){
            mEtConvert.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[$,.]", "");

            if (!TextUtils.isEmpty(cleanString)) {
                double parsed = Double.parseDouble(cleanString);
                String formatted = NumberFormat.getCurrencyInstance(Locale.US).format((parsed / 100));

                mCurrent = formatted;
                mEtConvert.setText(formatted);
                mEtConvert.setSelection(formatted.length());
                sendValue(formatted);
            }

            mEtConvert.addTextChangedListener(this);

        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (view != mEtConvert) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        return false;
    }

    private static class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {

            if (position >= .01F && position <= 1.0F) {
                view.setAlpha(1.0F);
            } else {
                view.setAlpha(0.5F);
            }
        }
    }
}
