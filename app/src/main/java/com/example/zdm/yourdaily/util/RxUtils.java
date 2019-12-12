package com.example.zdm.yourdaily.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 统一线程处理
 * Created by ZDM on 2019/12/12.
 */
public class RxUtils {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> applyIoSchedulers() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
