package com.rendecano.cashmoney.presentation.presenter;

import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.domain.interactor.ConvertUseCase;
import com.rendecano.cashmoney.domain.subscriber.UseCaseSubscriber;
import com.rendecano.cashmoney.presentation.presenter.view.CashMoneyView;

/**
 * Created by Ren Decano.
 */
public class CashMoneyPresenter implements Presenter<CashMoneyView> {

    private CashMoneyView moneyView;
    private ConvertUseCase useCase;

    public CashMoneyPresenter() {
        useCase = new ConvertUseCase();
    }

    public void initialize() {
        moneyView.showLoading();
        useCase.getInitialValues(new GetDefaultValuesSubscriber());
    }

    public void convertCurrency(double pValue, String pSymbol) {
        useCase.convertCurrency(pValue, pSymbol, new ConvertSubscriber());
    }

    @Override
    public void attachView(CashMoneyView view) {
        moneyView = view;
    }

    @Override
    public void destroy() {
        useCase = null;
    }

    private class GetDefaultValuesSubscriber extends UseCaseSubscriber<Base> {

        @Override
        public void onError(String message) {
            moneyView.hideLoading();
            moneyView.showError(message);
        }

        @Override
        public void onNext(Base base) {
            moneyView.hideLoading();
            moneyView.initialValues(base);
        }
    }

    private class ConvertSubscriber extends UseCaseSubscriber<String> {

        @Override
        public void onError(String message) {
            moneyView.showError(message);
        }

        @Override
        public void onNext(String pRate) {
            moneyView.showConvertedRate(pRate);
        }
    }
}
