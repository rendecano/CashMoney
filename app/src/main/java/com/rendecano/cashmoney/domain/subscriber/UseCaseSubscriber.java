package com.rendecano.cashmoney.domain.subscriber;

/**
 * Created by Ren Decano.
 */
public abstract class UseCaseSubscriber<T> {

    public void onError(String message) {
    }

    public void onNext(T t) {
    }
}
