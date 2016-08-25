package com.rendecano.cashmoney.presentation.presenter;

import com.rendecano.cashmoney.presentation.presenter.view.DefaultView;

/**
 * Created by Ren Decano.
 */

public interface Presenter<T extends DefaultView> {

    void attachView(T view);

    void destroy();

}
