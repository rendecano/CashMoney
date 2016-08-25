package com.rendecano.cashmoney.presentation.presenter.view;

import com.rendecano.cashmoney.data.entity.Base;

/**
 * Created by Ren Decano.
 */

public interface CashMoneyView extends DefaultView {

    void initialValues(Base base);

    void showConvertedRate(String pValue);

}
