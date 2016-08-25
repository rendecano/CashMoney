package com.rendecano.cashmoney.domain.interactor;

import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.data.repository.ConvertDataRepository;
import com.rendecano.cashmoney.domain.ConvertRepository;
import com.rendecano.cashmoney.domain.subscriber.UseCaseSubscriber;

/**
 * Created by Ren Decano.
 */

public class ConvertUseCase {

    private ConvertRepository repository;

    public ConvertUseCase() {
        repository = new ConvertDataRepository();
    }

    public void getInitialValues(UseCaseSubscriber<Base> subscriber) {
        repository.initialValues(subscriber);
    }

    public void convertCurrency(String pValue, String symbol, UseCaseSubscriber<String> subscriber) {
        repository.getConvertedCurrency(pValue, symbol, subscriber);
    }

}
