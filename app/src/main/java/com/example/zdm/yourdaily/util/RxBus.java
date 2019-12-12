package com.example.zdm.yourdaily.util;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by ZDM on 2019/12/12.
 */
public class RxBus {

    private static volatile RxBus mInstance;

    private final Subject bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例模式RxBus
     *
     * @return
     */
    public static RxBus getInstance() {

        RxBus rxBus = mInstance;
        if (mInstance == null) {
            synchronized (RxBus.class) {
                rxBus = mInstance;
                if (mInstance == null) {
                    rxBus = new RxBus();
                    mInstance = rxBus;
                }
            }
        }

        return rxBus;
    }

    /**
     * 发送消息
     *
     * @param object
     */
    public void post(Object object) {

        bus.onNext(object);

    }

    /**
     * 接收消息
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> onEvent(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
