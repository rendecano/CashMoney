package com.rendecano.cashmoney.domain;

import com.rendecano.cashmoney.data.entity.Base;
import com.rendecano.cashmoney.domain.subscriber.UseCaseSubscriber;

/**
 * Created by Ren Decano.
 */

public interface ConvertRepository {

    void initialValues(UseCaseSubscriber<Base> subscriber);

    void getConvertedCurrency(String pValue, String symbol, UseCaseSubscriber<String> subscriber);

}
