package com.amazonaws.config;

import com.amazonaws.dao.OrderFileDao;
import com.amazonaws.handlers.QueueConsumerLambda;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {OrderModule.class})
public interface OrderFileComponent {
    OrderFileDao provideOrderDao();
    void inject(QueueConsumerLambda requestHandler);
}
